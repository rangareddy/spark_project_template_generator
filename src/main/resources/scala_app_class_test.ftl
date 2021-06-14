package ${projectBuilder.packageName}

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import ${projectBuilder.packageName}.${projectBuilder.className}.{getRangeDS, countRangeDS}

class ${projectBuilder.className}Test extends FunSuite with BeforeAndAfterAll {

    @transient var spark: SparkSession = _

    override def beforeAll(): Unit = {
        val appName = "${projectBuilder.className}Test"
        val sparkConf = new SparkConf().setAppName(appName).setIfMissing("spark.master", "local[2]")
        spark = SparkSession.builder().config(sparkConf).getOrCreate()
    }

    override def afterAll(): Unit = {
        spark.stop()
    }

    test("Get Range DS") {
        val rangeDS = getRangeDS(spark)
        val rangeCount = rangeDS.count()
        assert(rangeCount == 1000, "Range count should be 1000")
    }

    test("Count Range DS") {
        val rangeDS = getRangeDS(spark)
        val rangeCount = countRangeDS(rangeDS)
        assert(rangeCount == 1000, "Range count should be 1000")
    }
}