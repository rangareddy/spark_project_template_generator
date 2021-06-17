package com.ranga.spark.project.template.api.java;

public class HelloWorldJavaTemplate extends JavaBaseTemplate {

    public HelloWorldJavaTemplate(String className) {
        super(className);
    }

    @Override
    public String codeTemplate() {
        return  "// Creating a dataset\n" +
                "        Dataset<Long> dataset = spark.range(1, 1000);\n" +
                "        dataset.printSchema();\n" +
                "        dataset.show();";
    }
}
