package com.ranga.spark.project.template;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParseDependencyVersions {
    public static void main(String[] args) {

        Map<String, String> componentPathMap = new LinkedHashMap<>();
        componentPathMap.put("sparkVersion", "/opt/cloudera/parcels/CDH/lib/spark/jars/*spark*");
        componentPathMap.put("hwcVersion", "/opt/cloudera/parcels/CDH/lib/hive_warehouse_connector/");
        componentPathMap.put("hbaseSparkVersion", "/opt/cloudera/parcels/CDH/lib/hbase_connectors/lib/*spark*");

        Map<String, String> componentResultMap = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry: componentPathMap.entrySet()) {
            String componentName = entry.getKey();
            String []componentPaths = entry.getValue().split(",");
            for(String componentPath : componentPaths) {
                File componentParent = new File(componentPath);
                if(componentParent.exists()) {
                    File[] jarFileList = componentParent.listFiles();
                    for(File jarFile : jarFileList) {
                        String fileName = jarFile.getName().replace(".jar", "");
                        int currentIndex = 0;
                        boolean isDigitFound = false;
                        while (!isDigitFound) {
                            if(currentIndex < fileName.length()) {
                                isDigitFound = Character.isDigit(fileName.charAt(currentIndex++));
                            } else {
                               break;
                            }
                        }
                        if(isDigitFound) {
                            String jarVersion = fileName.substring(currentIndex);
                            componentResultMap.put(componentName, jarVersion);
                        }
                    }
                }
            }
        }
    }
}

/*
ls /opt/cloudera/parcels/CDH/lib/spark/jars/*spark*
/opt/cloudera/parcels/CDH/lib/spark/jars/spark-avro_2.11-2.4.7.7.1.7.0-516.jar

ls /opt/cloudera/parcels/CDH/lib/hive_warehouse_connector/
hive-warehouse-connector-assembly-1.0.0.7.1.7.0-516.jar
pyspark_hwc-1.0.0.7.1.7.0-516.zip

ls /opt/cloudera/parcels/CDH/lib/hbase_connectors/lib/*spark*
/opt/cloudera/parcels/CDH/lib/hbase_connectors/lib/hbase-spark-1.0.0.7.1.7.0-516.jar

ls /opt/cloudera/parcels/CDH/lib/phoenix_connectors

 */