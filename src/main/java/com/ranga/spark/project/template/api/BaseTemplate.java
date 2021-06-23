package com.ranga.spark.project.template.api;

public interface BaseTemplate {
    String classTemplate();

    String importTemplate();

    String sparkSessionBuildTemplate();

    String codeTemplate();

    String methodsTemplate();

    String sparkSessionCloseTemplate();
}