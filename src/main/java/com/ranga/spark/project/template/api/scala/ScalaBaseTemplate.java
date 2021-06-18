package com.ranga.spark.project.template.api.scala;

import com.ranga.spark.project.template.api.BaseTemplate;

public abstract class ScalaBaseTemplate implements BaseTemplate {

    private String className;
    public ScalaBaseTemplate(String className) {
        this.className = className;
    }

    @Override
    public String classTemplate() {
        return "object "+className+" extends App with Serializable";
    }

    @Override
     public String importTemplate() {
        return "import java.lang\n" +
                "\n" +
                "import org.apache.spark.sql.{Dataset, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String sparkSessionBuildTemplate() {
         return "@transient lazy val logger: Logger = Logger.getLogger(getClass.getName)\n" +
                 "\n" +
                 "    val appName = \""+className+" Example\"\n" +
                 "\n" +
                 "    // Creating the SparkConf object\n" +
                 "    val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                 "\n" +
                 "    // Creating the SparkSession object\n" +
                 "    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()\n" +
                 "    logger.info(\"SparkSession created successfully\")";
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "    // Close the SparkSession\n" +
                "    spark.close()\n" +
                "    logger.info(\"SparkSession closed successfully\")";
    }
}