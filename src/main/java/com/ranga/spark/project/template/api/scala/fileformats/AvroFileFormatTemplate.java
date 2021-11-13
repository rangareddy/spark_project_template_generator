package com.ranga.spark.project.template.api.scala.fileformats;

import com.ranga.spark.project.template.api.scala.ScalaBaseTemplate;

public class AvroFileFormatTemplate extends ScalaBaseTemplate {

    public AvroFileFormatTemplate(String className) {
        super(className);
    }

    @Override
    public String methodsTemplate() {
<<<<<<< HEAD
        return  "// Get the Employee Dataset\n"+
                "    def getEmployeeDS(spark: SparkSession): Dataset[Row] = {\n" +
=======
        return "def getEmployeeDS(spark: SparkSession): Dataset[Row] = {\n" +
>>>>>>> 2f7505540355bf9d359b2ea9e33a6b721533bfd7
                "        import spark.implicits._\n" +
                "        Seq(\n" +
                "            Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "            Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "            Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "            Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "            Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDF()\n" +
                "    }\n" +
<<<<<<< HEAD
                "    // Display the Dataset\n" +
                "    def display(df: Dataset[Row]): Unit = {\n" +
                "        df.printSchema()\n" +
                "        df.show()\n" +
                "    }\n";
=======
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
>>>>>>> 2f7505540355bf9d359b2ea9e33a6b721533bfd7
    }

    @Override
    public String codeTemplate() {
<<<<<<< HEAD
        return  "val avroFilePath = \"/tmp/avro_data\"\n\n" +
                "        val employeeDF = getEmployeeDS(spark)\n" +
                "        display(employeeDF)\n" +
                "\n" +
                "        // write avro data\n" +
                "        df.coalesce(1).write.format(\"avro\").mode(\"overwrite\").save(avroFilePath)\n" +
                "\n" +
                "       // read avro data\n"+
                "        val avroEmployeeDF = spark.read.format(\"avro\").load(avroFilePath)\n" +
=======
        return "val employeeDF = getEmployeeDS(spark)\n" +
                "        employeeDF.printSchema()\n" +
                "\n" +
                "        // avro\n" +
                "        val avroFilePath = \"/tmp/avro_data\"\n" +
                "        saveData(employeeDF, \"avro\", avroFilePath)\n" +
                "\n" +
                "        val avroEmployeeDF = loadData(spark, \"avro\", avroFilePath)\n" +
>>>>>>> 2f7505540355bf9d359b2ea9e33a6b721533bfd7
                "        display(avroEmployeeDF)";
    }
}
