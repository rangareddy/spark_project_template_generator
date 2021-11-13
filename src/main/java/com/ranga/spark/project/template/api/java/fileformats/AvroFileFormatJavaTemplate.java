package com.ranga.spark.project.template.api.java.fileformats;

import com.ranga.spark.project.template.api.java.JavaBaseTemplate;

public class AvroFileFormatJavaTemplate extends JavaBaseTemplate {

    public AvroFileFormatJavaTemplate(String className) {
        super(className);
    }

    @Override
    public String codeTemplate() {
        return  "String avroFilePath = \"/tmp/avro_data\";\n\n" +
                "        // Get the Employee Dataset\n"+
                "        Dataset<EmployeeBean> employeeDF = getEmployeeDS(spark);\n" +
                "        display(employeeDF);\n\n"+
                "        // write avro data\n"+
                "        employeeDF.coalesce(1).write().format(\"avro\").mode(\"overwrite\").save(avroFilePath);\n\n"+
                "        // read avro data\n"+
                "        Dataset<Row> avroEmployeeDF = spark.read().format(\"avro\").load(avroFilePath);\n" +
    }

    @Override
    public String methodsTemplate() {
        return  "// Get the Employee Dataset\n"+
                "    public static Dataset<EmployeeBean> getEmployeeDS(SparkSession spark) {\n" +
                "        List<EmployeeBean> employeeData = new ArrayList<>();\n" +
                "        employeeData.add(new EmployeeBean(1l, \"Ranga Reddy\", 32, 80000.5f));\n" +
                "        employeeData.add(new EmployeeBean(2l, \"Nishanth Reddy\", 3, 180000.5f));\n" +
                "        employeeData.add(new EmployeeBean(3l, \"Raja Sekhar Reddy\", 59, 280000.5f));\n" +
                "        employeeData.add(new EmployeeBean(4l, \"Manoj Reddy\", 15, 8000.5f));\n" +
                "        employeeData.add(new EmployeeBean(5l, \"Vasundra Reddy\", 55, 580000.5f));\n" +
                "        return spark.createDataset(employeeData, Encoders.bean(EmployeeBean.class));\n" +
                "    }\n\n" +
                "    // Display the Dataset\n" +
                "    public static void display(Dataset<Row> dataset) {\n" +
                "        dataset.printSchema();\n" +
                "        dataset.show(false);\n" +
                "    }\n";
    }
}
