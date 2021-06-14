package ${projectBuilder.packageName};

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class ${projectBuilder.javaClassName} implements Serializable {

    private static final Logger logger = Logger.getLogger(${projectBuilder.javaClassName}.class.getName());

    public static void main(String[] args) {

        String appName = "${projectBuilder.javaClassName} Example";

        // Creating the SparkConf object
        SparkConf sparkConf = new SparkConf().setAppName(appName).setIfMissing("spark.master", "local[2]");

        // Creating the SparkSession object
        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
        logger.info("SparkSession created successfully");

        // Creating a dataset
        Dataset<Long> dataset = spark.range(1, 1000);
        dataset.printSchema();
        dataset.show();

        logger.info("${projectBuilder.appName} Finished");

        // Close the SparkSession
        spark.close();
        logger.info("SparkSession closed successfully");
    }
}