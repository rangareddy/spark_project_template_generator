package com.ranga.spark.project.template.api.scala;

public class KafkaTemplate extends ScalaBaseTemplate {

    public KafkaTemplate(String className) {
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
        return "import org.apache.spark.sql.SparkSession\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String codeTemplate() {
        return "        val kafkaBootstrapServers = args(0)\n" +
                "        val inputTopicNames = args(1)\n" +
                "\n" +
                "        val inputDf = spark\n" +
                "            .readStream\n" +
                "            .format(\"kafka\")\n" +
                "            .option(\"kafka.bootstrap.servers\", kafkaBootstrapServers)\n" +
                "            .option(\"subscribe\", inputTopicNames)\n" +
                "            .option(\"startingOffsets\", \"earliest\") // default for startingOffsets is \"latest\", but \"earliest\" allows rewind for missed alerts\n" +
                "            .load()\n" +
                "        \n" +
                "        inputDf.printSchema()\n" +
                "\n" +
                "        val outputDF =inputDf.writeStream\n" +
                "            .format(\"console\")\n" +
                "            .outputMode(\"append\")\n" +
                "            .option(\"truncate\", \"false\")\n" +
                "            .start()\n" +
                "\n" +
                "        outputDF.awaitTermination()";
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "";
    }
}