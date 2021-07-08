package com.ranga.spark.project.template.api.java;

public class FileFormatsJavaTemplate extends JavaBaseTemplate {

    public FileFormatsJavaTemplate(String className) {
        super(className);
    }

    @Override
    public String codeTemplate() {
        return "// Creating a dataset\n" +
                "        Dataset<EmployeeBean> employeeDF = getEmployeeDS(spark);\n" +
                "        employeeDF.printSchema();\n" +
                "        employeeDF.show(false);\n" +
                "\n" +
                "        // parquet\n" +
                "        String parquetFilePath = \"/tmp/parquet_data\";\n" +
                "        saveData(employeeDF, \"parquet\", parquetFilePath);\n" +
                "\n" +
                "        Dataset<Row> parquetEmployeeDF = loadData(spark, \"parquet\", parquetFilePath);\n" +
                "        display(parquetEmployeeDF);\n" +
                "\n" +
                "        // orc\n" +
                "        String orcFilePath = \"/tmp/orc_data\";\n" +
                "        saveData(employeeDF, \"orc\", orcFilePath);\n" +
                "\n" +
                "        Dataset<Row> orcEmployeeDF = loadData(spark, \"orc\", orcFilePath);\n" +
                "        display(orcEmployeeDF);\n" +
                "\n" +
                "        // avro\n" +
                "        String avroFilePath = \"/tmp/avro_data\";\n" +
                "        saveData(employeeDF, \"avro\", avroFilePath);\n" +
                "\n" +
                "        Dataset<Row> avroEmployeeDF = loadData(spark, \"avro\", avroFilePath);\n" +
                "        display(avroEmployeeDF);\n";
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
                "        return spark.createDataset(employeeData, Encoders.bean(EmployeeBean.class));\n" +
                "    }\n" +
                "\n" +
                "    public static void display(Dataset<Row> dataset) {\n" +
                "        dataset.printSchema();\n" +
                "        dataset.show(false);\n" +
                "    }\n" +
                "\n" +
                "    public static Dataset<Row> loadData(SparkSession spark, String format, String path) {\n" +
                "        Dataset<Row> employeeDF = spark.read().format(format).load(path);\n" +
                "        return employeeDF;\n" +
                "    }\n" +
                "\n" +
                "    public static void saveData(Dataset<EmployeeBean> employeeDF, String format, String path) {\n" +
                "        employeeDF.coalesce(1).write().format(format).mode(\"overwrite\").save(path);\n" +
                "    }";
    }
}