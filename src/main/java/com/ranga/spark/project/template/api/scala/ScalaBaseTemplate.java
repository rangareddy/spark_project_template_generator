package com.ranga.spark.project.template.api.scala;

import com.ranga.spark.project.template.api.BaseTemplate;

import static com.ranga.spark.project.template.util.AppConstants.*;

public abstract class ScalaBaseTemplate implements BaseTemplate {

    private final String className;

    public ScalaBaseTemplate(String className) {
        this.className = className;
    }

    @Override
    public String classTemplate() {
        return "object " + className + " extends Serializable";
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.{Dataset, Row, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String methodsTemplate() {
        return EMPTY_STRING;
    }

    @Override
    public String setupInstructions() {
        return EMPTY_STRING;
    }

    @Override
    public String sparkSessionBuildTemplate() {
        return NEW_LINE_DELIMITER +
                DOUBLE_TAB_DELIMITER + "// Creating the SparkConf object\n" +
                DOUBLE_TAB_DELIMITER + "val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                NEW_LINE_DELIMITER +
                DOUBLE_TAB_DELIMITER + "// Creating the SparkSession object\n" +
                DOUBLE_TAB_DELIMITER + "val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()\n" +
                DOUBLE_TAB_DELIMITER + "logger.info(\"SparkSession created successfully\")";
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "// Close the SparkSession\n" +
                DOUBLE_TAB_DELIMITER+"spark.close()\n" +
                DOUBLE_TAB_DELIMITER+"logger.info(\"SparkSession closed successfully\")";
    }
}