package com.ranga.spark.project.template.api.scala;

public class KuduTemplate extends ScalaBaseTemplate {

    public KuduTemplate(String className) {
        super(className);
    }

    @Override
    public String setupInstructions() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.SparkSession\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger\n" +
                "import collection.JavaConverters._\n" +
                "import org.apache.kudu.client._\n" +
                "import org.apache.kudu.spark.kudu.KuduContext";
    }

    @Override
    public String codeTemplate() {
        return "import spark.implicits._\n" +
                "        val employeeDF = Seq(\n" +
                "          Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "          Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "          Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "          Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "          Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDF()\n" +
                "\n" +
                "        val master = args(0)\n" +
                "        val kuduMasters = if(!master.contains(\":7051\")) s\"${master}:7051\" else master\n" +
                "\n" +
                "        // If table is created from Impala then table name prefix with \"impala::\"\n" +
                "        val kuduTableName = \"default.employees\"\n" +
                "        // Use KuduContext to create, delete, or write to Kudu tables\n" +
                "        val kuduContext = new KuduContext(kuduMasters, spark.sparkContext)\n" +
                "\n" +
                "        // Check for the existence of a Kudu table\n" +
                "        val isTableExists = kuduContext.tableExists(kuduTableName)\n" +
                "\n" +
                "        if(!isTableExists) {\n" +
                "            // Create a new Kudu table from a DataFrame schema\n" +
                "            kuduContext.createTable(kuduTableName, employeeDF.schema, Seq(\"id\"),\n" +
                "                new CreateTableOptions()\n" +
                "                  .setNumReplicas(1)\n" +
                "                  .addHashPartitions(List(\"id\").asJava, 3))\n" +
                "        }\n" +
                "\n" +
                "        // Insert data\n" +
                "        kuduContext.insertRows(employeeDF, kuduTableName)\n" +
                "\n" +
                "        // Read a table from Kudu\n" +
                "        val empDF = spark.read\n" +
                "          .options(Map(\"kudu.master\" -> kuduMasters, \"kudu.table\" -> kuduTableName))\n" +
                "          .format(\"kudu\").load\n" +
                "\n" +
                "        empDF.printSchema()\n" +
                "        empDF.show()\n" +
                "\n" +
                "        // Delete data\n" +
                "        kuduContext.deleteRows(employeeDF, kuduTableName)\n" +
                "\n" +
                "        // Upsert data\n" +
                "        kuduContext.upsertRows(employeeDF, kuduTableName)\n" +
                "\n" +
                "        // Update data\n" +
                "        val updateEmployeeDF = employeeDF.select($\"age\", ($\"age\" + 1).as(\"age_val\"))\n" +
                "        kuduContext.updateRows(updateEmployeeDF, kuduTableName)\n" +
                "\n" +
                "        // Delete a Kudu table\n" +
                "        kuduContext.deleteTable(kuduTableName)";
    }
}