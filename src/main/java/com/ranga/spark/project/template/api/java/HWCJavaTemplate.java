package com.ranga.spark.project.template.api.java;

public class HWCJavaTemplate extends JavaBaseTemplate {

    public HWCJavaTemplate(String className) {
        super(className);
    }

    @Override
    public String importTemplate() {
        return super.importTemplate() +
                "\nimport com.hortonworks.hwc.HiveWarehouseSession;";
    }

    @Override
    public String codeTemplate() {
        return "String database_name = \"hwc_db\";\n" +
                "        String table_name = \"employee\";\n" +
                "        String database_table_name = database_name +\".\"+table_name;\n" +
                "        HiveWarehouseSession hive = HiveWarehouseSession.session(spark).build();\n" +
                "        logger.info(\"HiveWarehouseSession created successfully\");"+
                "\n" +
                "        // Create a Database\n" +
                "        hive.createDatabase(database_name, true);\n" +
                "\n" +
                "        // Display all databases\n" +
                "        hive.showDatabases().show(false);\n" +
                "\n" +
                "        // Use database\n" +
                "        hive.setDatabase(database_name);\n" +
                "\n" +
                "        // Display all tables\n" +
                "        hive.showTables().show(false);\n" +
                "\n" +
                "        // Drop a table\n" +
                "        hive.dropTable(database_table_name, true, true);\n" +
                "\n" +
                "        // Create a Table\n" +
                "        hive.createTable(database_table_name).ifNotExists()\n" +
                "        .column(\"id\", \"bigint\")           \n" +
                "        .column(\"name\", \"string\")           \n" +
                "        .column(\"age\", \"smallint\")           \n" +
                "        .column(\"salary\", \"float\").create();\n" +
                "\n" +
                "        // Creating a dataset\n" +
                "        Dataset<EmployeeBean> employeeDF = getEmployeeDS(spark);\n" +
                "        employeeDF.printSchema();\n" +
                "        employeeDF.show(false);\n" +
                "        \n" +
                "        // Save the data\n" +
                "        saveEmployeeData(employeeDF, database_table_name);\n" +
                "        \n" +
                "        // Select the data\n" +
                "        Dataset<Row> empDF = getEmployeeData(hive, database_table_name);\n" +
                "        empDF.printSchema();\n" +
                "        empDF.show(false);";
    }

    @Override
    public String methodsTemplate() {
        return "public static Dataset<EmployeeBean> getEmployeeDS(SparkSession spark) {\n" +
                "        List<EmployeeBean> employeeData = new ArrayList<>();\n" +
                "        employeeData.add(new EmployeeBean(1l, \"Ranga Reddy\", 32, 80000.5f));\n" +
                "        employeeData.add(new EmployeeBean(2l, \"Nishanth Reddy\", 3, 180000.5f));\n" +
                "        employeeData.add(new EmployeeBean(3l, \"Raja Sekhar Reddy\", 59, 280000.5f));\n" +
                "        employeeData.add(new EmployeeBean(4l, \"Manoj Reddy\", 15, 8000.5f));\n" +
                "        employeeData.add(new EmployeeBean(5l, \"Vasundra Reddy\", 55, 580000.5f));\n" +
                "\n" +
                "        return spark.createDataset(employeeData, Encoders.bean(EmployeeBean.class));\n" +
                "    }\n" +
                "\n" +
                "    public static void saveEmployeeData(Dataset<EmployeeBean> employeeDF, String tableName) {\n" +
                "        employeeDF.write().format(\"com.hortonworks.spark.sql.hive.llap.HiveWarehouseConnector\").\n" +
                "                mode(\"append\").option(\"table\", tableName).save();\n" +
                "    }\n" +
                "    \n" +
                "    public static Dataset<Row> getEmployeeData(HiveWarehouseSession hive, String tableName) {\n" +
                "        return hive.executeQuery(\"SELECT * FROM \"+tableName);\n" +
                "    }";
    }
}
