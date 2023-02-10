package com.ranga.spark.project.template.api.scala;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeltaTableTemplate extends ScalaBaseTemplate {

    private String deltaVersion = "";

    public DeltaTableTemplate(String className, String deltaVersion) {
        super(className);
        Map<String, Object> configMap = new LinkedHashMap<>();
        configMap.put("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension");
        configMap.put("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog");
        setConfigMap(configMap);
        this.deltaVersion = deltaVersion;
    }

    @Override
    public String setupInstructions() {

        StringBuilder setUpInstructionSB = new StringBuilder();
        setUpInstructionSB.append("## Run the code Interactively\n\n");

        setUpInstructionSB
                .append("### Spark Scala Shell\n\n")
                .append("```sh\n")
                .append("spark-shell --packages io.delta:delta-core_${delta_version} \\ \n")
                .append("\t--conf \"spark.sql.extensions=io.delta.sql.DeltaSparkSessionExtension\" \\ \n")
                .append("\t--conf \"spark.sql.catalog.spark_catalog=org.apache.spark.sql.delta.catalog.DeltaCatalog\"\n")
                .append("```\n");

        setUpInstructionSB
                .append("#### Create a table\n\n")
                .append("```scala\n")
                .append("val data = spark.range(0, 5)\n")
                .append("data.write.format(\"delta\").save(\"/tmp/delta-table\")\n")
                .append("```\n\n");

        setUpInstructionSB
                .append("#### Read data\n\n")
                .append("```scala\n")
                .append("val df = spark.read.format(\"delta\").load(\"/tmp/delta-table\")\n")
                .append("df.show()\n")
                .append("```\n\n");

        setUpInstructionSB
                .append("### PySpark Shell\n\n")
                .append("```sh\n")
                .append("pyspark --packages io.delta:delta-core_${delta_version} \\ \n")
                .append("\t--conf \"spark.sql.extensions=io.delta.sql.DeltaSparkSessionExtension\" \\ \n")
                .append("\t--conf \"spark.sql.catalog.spark_catalog=org.apache.spark.sql.delta.catalog.DeltaCatalog\"\n")
                .append("```\n\n");

        setUpInstructionSB
                .append("#### Create a table\n\n")
                .append("```python\n")
                .append("data = spark.range(0, 5)\n")
                .append("data.write.format(\"delta\").save(\"/tmp/delta-table\")\n")
                .append("```\n\n");

        setUpInstructionSB
                .append("#### Read data\n\n")
                .append("```python\n")
                .append("df = spark.read.format(\"delta\").load(\"/tmp/delta-table\")\n")
                .append("df.show()\n")
                .append("```");

        return setUpInstructionSB.toString().replace("${delta_version}", deltaVersion);
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.{Dataset, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger\n" +
                "import org.apache.commons.io.FileUtils\n" +
                "import java.io.File\n";
    }

    @Override
    public String methodsTemplate() {
        return "def getEmployeeDS(spark: SparkSession): Dataset[Employee] = {\n" +
                "        import spark.implicits._\n" +
                "        Seq(\n" +
                "            Employee(1L, \"Ranga Reddy\", 32, 80000.5f),\n" +
                "            Employee(2L, \"Nishanth Reddy\", 3, 180000.5f),\n" +
                "            Employee(3L, \"Raja Sekhar Reddy\", 59, 280000.5f),\n" +
                "            Employee(4L, \"Manoj Reddy\", 15, 8000.5f),\n" +
                "            Employee(5L, \"Vasundra Reddy\", 55, 580000.5f)\n" +
                "        ).toDS()\n" +
                "    }";
    }

    @Override
    public String codeTemplate() {
        return "val deltaPath = \"/tmp/delta-table\"\n" +
                "        val deltaFile = new File(deltaPath)\n" +
                "        if (deltaFile.exists()) FileUtils.deleteDirectory(deltaFile)\n" +
                "\n" +
                "        val employeeDS = getEmployeeDS(spark)\n" +
                "        employeeDS.show()\n" +
                "\n" +
                "        // Create a table\n" +
                "        logger.info(\"Creating a table\")\n" +
                "        employeeDS.write.format(\"delta\").save(deltaPath)\n" +
                "\n" +
                "        // Read table\n" +
                "        logger.info(\"Reading the table\")\n" +
                "        val employeeDF = spark.read.format(\"delta\").load(deltaPath)\n" +
                "        employeeDF.show()";
    }
}
