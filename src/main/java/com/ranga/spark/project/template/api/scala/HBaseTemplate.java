package com.ranga.spark.project.template.api.scala;

public class HBaseTemplate extends ScalaBaseTemplate {

    public HBaseTemplate(String className) {
        super(className);
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.SparkSession\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
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
                "    val columnMapping = \"id Long :key, name STRING e:name, age Integer e:age, salary FLOAT e:salary\"\n" +
                "    val format = \"org.apache.hadoop.hbase.spark\"\n" +
                "    val tableName = \"employees\"\n" +
                "\n" +
                "    val options = Map( \"hbase.columns.mapping\" -> columnMapping, \"hbase.spark.use.hbasecontext\" -> \"false\", \"hbase.table\" -> tableName)\n" +
                "\n" +
                "    // write the data to hbase table\n" +
                "    employeeDS.write.format(format).\n" +
                "      options(options).\n" +
                "      save()\n" +
                "\n" +
                "    // read the data from hbase table\n" +
                "    val df = spark.read.format(format).\n" +
                "      options(options).\n" +
                "      load()\n" +
                "\n" +
                "    // display the data\n" +
                "    df.show(truncate=false)";
    }
}