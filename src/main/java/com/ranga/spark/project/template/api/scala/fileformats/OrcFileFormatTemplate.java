package com.ranga.spark.project.template.api.scala.fileformats;

import com.ranga.spark.project.template.api.scala.ScalaBaseTemplate;

public class OrcFileFormatTemplate extends ScalaBaseTemplate {

    public OrcFileFormatTemplate(String className) {
        super(className);
    }

    @Override
    public String methodsTemplate() {
        return "def getEmployeeDS(spark: SparkSession): Dataset[Row] = {\n" +
                "        import spark.implicits._\n" +
                "        Seq(\n" +
                "            Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "            Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "            Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "            Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "            Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDF()\n" +
                "    }\n" +
                "\n" +
                "    def saveData(df: Dataset[Row], format:String, path: String): Unit = {\n" +
                "        df.coalesce(1).write.format(format).mode(\"overwrite\").save(path)\n" +
                "    }\n" +
                "    def display(df: Dataset[Row]): Unit = {\n" +
                "        df.printSchema()\n" +
                "        df.show()\n" +
                "    }\n" +
                "\n" +
                "    def loadData(spark: SparkSession, format:String, path: String) : Dataset[Row] = {\n" +
                "        spark.read.format(format).load(path)\n" +
                "    }";
    }

    @Override
    public String codeTemplate() {
        return "val employeeDF = getEmployeeDS(spark)\n" +
                "        employeeDF.printSchema()\n" +
                "\n" +
                "        // orc\n" +
                "        val orcFilePath = \"/tmp/orc_data\"\n" +
                "        saveData(employeeDF, \"orc\", orcFilePath)\n" +
                "\n" +
                "        val orcEmployeeDF = loadData(spark, \"orc\", orcFilePath)\n" +
                "        display(orcEmployeeDF)\n" +
                "\n";
    }
}
