package com.ranga.spark.project.template.api.scala.cloud;

import com.ranga.spark.project.template.api.scala.ScalaBaseTemplate;

public class S3Template extends ScalaBaseTemplate {

    public S3Template(String className) {
        super(className);
    }

    @Override
    public String setupInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
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
        return  "\n" +
                "        val awsAccessKey = args(0)\n" +
                "        val awsSecretKey = args(1)\n" +
                "        val bucketName = args(2)\n" +
                "\n" +
                "        // S3 settings\n" +
                "        val conf = spark.sparkContext.hadoopConfiguration\n" +
                "        conf.set(\"spark.hadoop.fs.s3a.access.key\", awsAccessKey)\n" +
                "        conf.set(\"spark.hadoop.fs.s3a.secret.key\", awsSecretKey)\n" +
                "        conf.set(\"spark.hadoop.fs.s3a.impl\", \"org.apache.hadoop.fs.s3a.S3AFileSystem\")\n" +
                "        conf.set(\"spark.speculation\", \"false\")\n" +
                "        conf.set(\"spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version\", \"2\")\n" +
                "        conf.set(\"spark.hadoop.mapreduce.fileoutputcommitter.cleanup-failures.ignored\", \"true\")\n" +
                "        conf.set(\"fs.s3a.experimental.input.fadvise\", \"random\")\n"+
                "\n"+
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
                "        // Define the s3 destination path\n" +
                "        val s3_dest_path = \"s3a://\" + bucketName + \"/employees\"\n" +
                "\n" +
                "        // Write the data as Orc\n" +
                "        val employeeOrcPath = s3_dest_path + \"/employee_orc\"\n" +
                "        employeeDF.write.mode(\"overwrite\").format(\"orc\").save(employeeOrcPath)\n" +
                "\n" +
                "        // Read the employee orc data\n" +
                "        val employeeOrcData = spark.read.format(\"orc\").load(employeeOrcPath)\n" +
                "        employeeOrcData.printSchema()\n" +
                "        employeeOrcData.show()\n" +
                "\n" +
                "        // Write the data as Parquet\n" +
                "        val employeeParquetPath = s3_dest_path + \"/employee_parquet\"\n" +
                "        employeeOrcData.write.mode(\"overwrite\").format(\"parquet\").save(employeeParquetPath)\n";
    }
}