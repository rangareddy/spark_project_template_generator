package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class CodeTemplateBean implements Serializable {

    private String importTemplate;
    private String classTemplate;
    private String sparkSessionBuildTemplate;
    private String sparkSessionCloseTemplate;
    private String codeTemplate;
    private String methodsTemplate;

    public String getImportTemplate() {
        return importTemplate;
    }

    public void setImportTemplate(String importTemplate) {
        this.importTemplate = importTemplate;
    }

    public String getClassTemplate() {
        return classTemplate;
    }

    public void setClassTemplate(String classTemplate) {
        this.classTemplate = classTemplate;
    }

    public String getSparkSessionBuildTemplate() {
        return sparkSessionBuildTemplate;
    }

    public void setSparkSessionBuildTemplate(String sparkSessionBuildTemplate) {
        this.sparkSessionBuildTemplate = sparkSessionBuildTemplate;
    }

    public String getSparkSessionCloseTemplate() {
        return sparkSessionCloseTemplate;
    }

    public void setSparkSessionCloseTemplate(String sparkSessionCloseTemplate) {
        this.sparkSessionCloseTemplate = sparkSessionCloseTemplate;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public String getMethodsTemplate() {
        return methodsTemplate;
    }

    public void setMethodsTemplate(String methodsTemplate) {
        this.methodsTemplate = methodsTemplate;
    }

    @Override
    public String toString() {
        return "CodeTemplateBean{" +
                "importTemplate='" + importTemplate + '\'' +
                ", classTemplate='" + classTemplate + '\'' +
                ", sparkSessionBuildTemplate='" + sparkSessionBuildTemplate + '\'' +
                ", sparkSessionCloseTemplate='" + sparkSessionCloseTemplate + '\'' +
                ", codeTemplate='" + codeTemplate + '\'' +
                ", methodsTemplate='" + methodsTemplate + '\'' +
                '}';
    }
}
