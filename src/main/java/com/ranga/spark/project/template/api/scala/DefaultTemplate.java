package com.ranga.spark.project.template.api.scala;

public class DefaultTemplate extends ScalaBaseTemplate {

    public DefaultTemplate(String className) {
        super(className);
    }

    @Override
    public String methodsTemplate() {
        return "    def getEmployeeDS(spark: SparkSession): Dataset[Employee] = {\n" +
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
                "    def getEmployeeCount(employeeDS: Dataset[Employee]): Long = {\n" +
                "        employeeDS.count()\n" +
                "    }";
    }

    @Override
    public String codeTemplate() {
        return "val employeeDS = getEmployeeDS(spark)\n" +
                "        employeeDS.printSchema()\n" +
                "        val employeeCount = getEmployeeCount(employeeDS)\n" +
                "        logger.info(\"Employees count \"+employeeCount)";
    }
}
