package com.ranga.spark.project.template.api.scala.cloud;

import com.ranga.spark.project.template.api.scala.ScalaBaseTemplate;

public class GcsTemplate extends ScalaBaseTemplate {

    public GcsTemplate(String className) {
        super(className);
    }

    @Override
    public String setupInstructions() {
        StringBuilder sb = new StringBuilder("");
        return sb.toString();
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.{SQLContext, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String codeTemplate() {
        return  "\n"+
                "        val projectId = args(0)\n" +
                "        val bucketName = args(1)\n" +
                "        val privateKey = args(2)\n" +
                "        val privateKeyId = args(3)\n" +
                "        val clientEmail = args(4)\n" +
                "\n" +
                "        // GCS settings\n" +
                "        val conf = spark.sparkContext.hadoopConfiguration\n" +
                "        conf.set(\"fs.gs.impl\", \"com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem\")\n" +
                "        conf.set(\"fs.AbstractFileSystem.gs.impl\", \"com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS\")\n" +
                "        conf.set(\"fs.gs.auth.service.account.enable\", \"true\")\n" +
                "        conf.set(\"fs.gs.project.id\", projectId)\n" +
                "        conf.set(\"fs.gs.auth.service.account.private.key\", privateKey)\n" +
                "        conf.set(\"fs.gs.auth.service.account.private.key.id\", privateKeyId)\n" +
                "        conf.set(\"fs.gs.auth.service.account.email\", clientEmail)\n" +
                "\n" +
                "        import spark.implicits._\n" +
                "        val employeeDF = Seq(\n" +
                "          Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "          Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "          Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "          Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "          Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDF()\n" +
                "        \n" +
                "        employeeDF.printSchema()\n" +
                "        employeeDF.show()\n" +
                "\n" +
                "        // Define the gcs destination path\n" +
                "        val gcs_dest_path = \"gs://\" + bucketName + \"/employees\"\n" +
                "\n" +
                "        // Write the data as Orc\n" +
                "        val employeeOrcPath = gcs_dest_path + \"/employee_orc\"\n" +
                "        employeeDF.write.mode(\"overwrite\").format(\"orc\").save(employeeOrcPath)\n" +
                "\n" +
                "        // Read the employee orc data\n" +
                "        val employeeOrcData = spark.read.format(\"orc\").load(employeeOrcPath)\n" +
                "        employeeOrcData.printSchema()\n" +
                "        employeeOrcData.show()\n" +
                "\n" +
                "        // Write the data as Parquet\n" +
                "        val employeeParquetPath = gcs_dest_path + \"/employee_parquet\"\n" +
                "        employeeOrcData.write.mode(\"overwrite\").format(\"parquet\").save(employeeParquetPath)";
    }
}