package com.ranga.spark.project.template.api.scala;

public class HelloWorldTemplate extends ScalaBaseTemplate {

    public HelloWorldTemplate(String className) {
        super(className);
    }

    @Override
    public String codeTemplate() {
        return  "val rangeDS = getRangeDS(spark)\n" +
                "    val count = countRangeDS(rangeDS)\n" +
                "    logger.info(s\"Range count ${count}\")" +
                "\n" +
                "\n" +
                "    def getRangeDS(spark: SparkSession, start: Long = 0, end: Long = 1000): Dataset[lang.Long] = {\n" +
                "        spark.range(start, end)\n" +
                "    }\n" +
                "\n" +
                "    def countRangeDS(rangeDS: Dataset[lang.Long]): Long = {\n" +
                "        rangeDS.count()\n" +
                "    }";
    }
}
