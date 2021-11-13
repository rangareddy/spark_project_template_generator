package com.ranga.spark.project.template.api.java;

public class DefaultJavaTemplate extends JavaBaseTemplate {

    public DefaultJavaTemplate(String className) {
        super(className);
    }

    @Override
    public String codeTemplate() {
        return "// Creating a dataset\n" +
                "        Dataset<EmployeeBean> employeeDF = getEmployeeDS(spark);\n" +
                "        employeeDF.printSchema();\n" +
                "        employeeDF.show(false);\n" +
                "        \n" +
                "        long employeeCount = getEmployeeCount(employeeDF);\n" +
                "        logger.info(\"Employees count \"+employeeCount);";
    }

    @Override
    public String methodsTemplate() {
        return "    public static Dataset<EmployeeBean> getEmployeeDS(SparkSession spark) {\n" +
                "        List<EmployeeBean> employeeData = new ArrayList<>();\n" +
                "        employeeData.add(new EmployeeBean(1l, \"Ranga Reddy\", 32, 80000.5f));\n" +
                "        employeeData.add(new EmployeeBean(2l, \"Nishanth Reddy\", 3, 180000.5f));\n" +
                "        employeeData.add(new EmployeeBean(3l, \"Raja Sekhar Reddy\", 59, 280000.5f));\n" +
                "        employeeData.add(new EmployeeBean(4l, \"Manoj Reddy\", 15, 8000.5f));\n" +
                "        employeeData.add(new EmployeeBean(5l, \"Vasundra Reddy\", 55, 580000.5f));\n" +
                "\n" +
                "        return spark.createDataset(employeeData, Encoders.bean(EmployeeBean.class));\n" +
                "    }\n" +
                "    \n" +
                "    public static Long getEmployeeCount(Dataset<EmployeeBean> employeeDataset) {\n" +
                "        return employeeDataset.count();\n" +
                "    }";
    }
}
