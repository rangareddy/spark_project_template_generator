package com.ranga.spark.project.template.api;

public interface BaseTemplate {
    String importTemplate();
    String sparkSessionBuildTemplate();
    String codeTemplate();
    String sparkSessionCloseTemplate();
}