package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class ProjectDetailBean implements Serializable {

    private String projectName;
    private String templateName = "default";
    private String projectVersion;
    private String projectDir;
    private String packageName;
    private String delimiter = "-";
    private String projectExtension = "integration";

    public ProjectDetailBean() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSourceProjectName() {
        String projectNameExtension = projectExtension.toLowerCase();
        StringBuilder sourceProjectSB = new StringBuilder(projectName);
        if (projectNameExtension != null && !projectName.toLowerCase().endsWith(projectNameExtension)) {
            sourceProjectSB.append(Character.toUpperCase(projectNameExtension.charAt(0)));
            if (projectNameExtension.length() > 1) {
                sourceProjectSB.append(projectNameExtension.substring(1));
            }
        }
        return sourceProjectSB.toString();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getProjectExtension() {
        return projectExtension;
    }

    public void setProjectExtension(String projectExtension) {
        this.projectExtension = projectExtension;
    }

    @Override
    public String toString() {
        return "ProjectDetailBean{" +
                "projectName='" + projectName + '\'' +
                ", templateName='" + templateName + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", projectDir='" + projectDir + '\'' +
                ", packageName='" + packageName + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", projectExtension='" + projectExtension + '\'' +
                '}';
    }
}