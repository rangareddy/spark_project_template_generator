package com.ranga.spark.project.template.api.java;

import com.ranga.spark.project.template.api.BaseTemplate;

public abstract class JavaBaseTemplate implements BaseTemplate {

    private String className;
    public JavaBaseTemplate(String className) {
        this.className = className;
    }

    @Override
    public String classTemplate() {
        return  "public class "+ className +" implements Serializable";
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.SparkConf;\n" +
                "import org.apache.spark.sql.Dataset;\n" +
                "import org.apache.spark.sql.SparkSession;\n" +
                "import org.apache.log4j.Logger;\n" +
                "import java.io.Serializable;";
    }

    @Override
    public String sparkSessionBuildTemplate() {
        return "String appName = \" "+className +" Example\";\n" +
                "\n" +
                "        // Creating the SparkConf object\n" +
                "        SparkConf sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\");\n" +
                "\n" +
                "        // Creating the SparkSession object\n" +
                "        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();\n" +
                "        logger.info(\"SparkSession created successfully\");";
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "// Close the SparkSession\n" +
                "        spark.close();\n" +
                "        logger.info(\"SparkSession closed successfully\");";
    }
}