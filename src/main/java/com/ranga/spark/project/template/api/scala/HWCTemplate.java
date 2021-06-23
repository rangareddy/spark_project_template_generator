package com.ranga.spark.project.template.api.scala;

public class HWCTemplate extends ScalaBaseTemplate {

    private final String className;

    public HWCTemplate(String className) {
        super(className);
        this.className = className;
    }

    @Override
    public String importTemplate() {
        return super.importTemplate() + "\n" +
                "import com.hortonworks.hwc.HiveWarehouseSession\n" +
                "import com.hortonworks.spark.sql.hive.llap.HiveWarehouseSessionImpl";
    }

    @Override
    public String sparkSessionBuildTemplate() {
        return "\n" +
                "    val appName = \"" + className + " Example\"\n" +
                "        // Creating the SparkConf object\n" +
                "        val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                "    \n" +
                "        // Creating the SparkSession object\n" +
                "        val spark: SparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()\n" +
                "        logger.info(\"SparkSession created successfully\")\n" +
                "            \n" +
                "        val hive = HiveWarehouseSession.session(spark).build()\n" +
                "        logger.info(\"HiveWarehouseSession created successfully\")";
    }

    @Override
    public String codeTemplate() {
        return "val database_name = \"hwc_db\";\n" +
                "        val table_name = \"employee\";\n" +
                "        val database_table_name = database_name +\".\"+table_name;\n" +
                "\n" +
                "        // Create a Database\n" +
                "        hive.createDatabase(database_name, true)\n" +
                "\n" +
                "        // Display all databases\n" +
                "        hive.showDatabases().show(false)\n" +
                "\n" +
                "        // Use database\n" +
                "        hive.setDatabase(database_name)\n" +
                "\n" +
                "        // Display all tables\n" +
                "        hive.showTables().show(false)\n" +
                "\n" +
                "        // Drop a table\n" +
                "        hive.dropTable(database_table_name, true, true)\n" +
                "\n" +
                "        // Create a Table\n" +
                "        hive.createTable(database_table_name).ifNotExists()\n" +
                "            .column(\"id\", \"bigint\")           \n" +
                "            .column(\"name\", \"string\")           \n" +
                "            .column(\"age\", \"smallint\")           \n" +
                "            .column(\"salary\", \"float\").create()\n" +
                "        \n" +
                "        // Creating a dataset\n" +
                "        val employeeDF = getEmployeeDS(spark)\n" +
                "        employeeDF.printSchema()\n" +
                "        employeeDF.show(false)\n" +
                "        \n" +
                "        // Save the data\n" +
                "        saveEmployeeData(employeeDF, database_table_name)\n" +
                "        \n" +
                "        // Select the data\n" +
                "        val empDF = getEmployeeData(hive, database_table_name)\n" +
                "        empDF.printSchema()\n" +
                "        empDF.show(truncate=false)\n";
    }

    @Override
    public String methodsTemplate() {
        return "def getEmployeeDS (spark: SparkSession) : Dataset[Employee] = {\n" +
                "        import spark.implicits._\n" +
                "        Seq(\n" +
                "            Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "            Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "            Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "            Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "            Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDS()\n" +
                "    }\n" +
                "\n" +
                "    def saveEmployeeData(employeeDF: Dataset[Employee], tableName: String): Unit = {\n" +
                "        employeeDF.write.format(\"com.hortonworks.spark.sql.hive.llap.HiveWarehouseConnector\").\n" +
                "          mode(\"append\").option(\"table\", tableName).save();\n" +
                "    }\n" +
                "\n" +
                "    def getEmployeeData(hive: HiveWarehouseSessionImpl, tableName: String) : Dataset[Row]  = {\n" +
                "        hive.executeQuery(\"SELECT * FROM \"+tableName)\n" +
                "    }";
    }
}