package ${projectBuilder.packageName}

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import com.ranga.hello.world.HelloWorldIntegrationApp.{getEmployeeCount, getEmployeeDS}

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

    test("Get Employee DS") {
        val employeeDS = getEmployeeDS(spark)
        val rangeCount = employeeDS.count()
        assert(rangeCount == 5, "Employee count should be 5")
    }

    test("Count Employee DS") {
        val rangeDS = getEmployeeDS(spark)
        val rangeCount = getEmployeeCount(rangeDS)
        assert(rangeCount == 5, "Employee count should be 1000")
    }
}