package ${projectBuilder.packageName}

import java.lang

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.SparkConf
import org.apache.log4j.Logger

object ${projectBuilder.className} extends App with Serializable {

    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

    val appName = "${projectBuilder.className} Example"

    // Creating the SparkConf object
    val sparkConf = new SparkConf().setAppName(appName).setIfMissing("spark.master", "local[2]")

    // Creating the SparkSession object
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    logger.info("SparkSession created successfully")

    val rangeDS = getRangeDS(spark)
    val count = countRangeDS(rangeDS)
    logger.info(s"Range count ${r"${count}"}")

    logger.info("${projectBuilder.appName} Finished")

    // Close the SparkSession
    spark.close()
    logger.info("SparkSession closed successfully")

    def getRangeDS(spark: SparkSession, start: Long = 0, end: Long = 1000): Dataset[lang.Long] = {
        spark.range(start, end)
    }

    def countRangeDS(rangeDS: Dataset[lang.Long]): Long = {
        rangeDS.count()
    }
}