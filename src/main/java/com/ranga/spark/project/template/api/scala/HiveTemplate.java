package com.ranga.spark.project.template.api.scala;

public class HiveTemplate extends ScalaBaseTemplate {

    private String className;
    public HiveTemplate(String className) {
        super(className);
        this.className = className;
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.SparkSession\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String sparkSessionBuildTemplate() {
        return  "    val appName = \""+className+" Example\"\n" +
                "\n" +
                "    // Creating the SparkConf object\n" +
                "    val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                "\n" +
                "    // Creating the SparkSession object\n" +
                "    val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()\n" +
                "    logger.info(\"SparkSession created successfully\")";
    }

    @Override
    public String codeTemplate() {
        return  "case class Employee(id:Long, name: String, age: Integer, salary: Float)\n" +
                "\n" +
                "    import spark.implicits._\n" +
                "    val employeeDS = Seq(\n" +
                "      Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "      Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "      Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "      Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "      Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "    ).toDS()\n" +
                "\n" +
                "    // write the data to hive table\n" +
                "    val tableName = \"default.employees\"\n" +
                "    employeeDS.write.mode(\"overwrite\").saveAsTable(tableName)\n" +
                "\n" +
                "    // read the data from hive table\n" +
                "    val df = spark.sql(s\"SELECT * FROM ${tableName}\")\n" +
                "\n" +
                "    // display the data\n" +
                "    df.show(truncate=false)";
    }
}