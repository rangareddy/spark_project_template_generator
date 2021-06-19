package com.ranga.spark.project.template.api.scala;

import com.ranga.spark.project.template.api.BaseTemplate;

public abstract class ScalaBaseTemplate implements BaseTemplate {

    private String className;
    public ScalaBaseTemplate(String className) {
        this.className = className;
    }

    @Override
    public String classTemplate() {
        return "object "+className+" extends Serializable";
    }

    @Override
     public String importTemplate() {
        return "import org.apache.spark.sql.{Dataset, Row, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String methodsTemplate() {
        return "";
    }

    @Override
    public String sparkSessionBuildTemplate() {
         return "val appName = \"HelloWorldIntegrationApp Example\"\n" +
                 "\n" +
                 "        // Creating the SparkConf object\n" +
                 "        val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                 "    \n" +
                 "        // Creating the SparkSession object\n" +
                 "        val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()\n" +
                 "        logger.info(\"SparkSession created successfully\")";
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "// Close the SparkSession\n" +
                "        spark.close()\n" +
                "        logger.info(\"SparkSession closed successfully\")";
    }
}