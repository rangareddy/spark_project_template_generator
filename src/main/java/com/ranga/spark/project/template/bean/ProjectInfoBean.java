package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.util.TemplateType;

import java.io.Serializable;

public class ProjectInfoBean implements Serializable {

    private String projectName;
    private String projectVersion;
    private String scalaVersion;
    private String scalaBinaryVersion;
    private String packageName;
    private String className;
    private String javaClassName;
    private String fullClassName;
    private String jarName;
    private String jarPath;
    private String jarVersion;
    private boolean isJavaTemplate;
    private String runScriptName;
    private String runScriptPath;
    private String readMePath;
    private String jarDeployPath;
    private String deployScriptPath;
    private TemplateType templateType;

    public ProjectInfoBean() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getScalaVersion() {
        return scalaVersion;
    }

    public void setScalaVersion(String scalaVersion) {
        this.scalaVersion = scalaVersion;
    }

    public String getScalaBinaryVersion() {
        return scalaBinaryVersion;
    }

    public void setScalaBinaryVersion(String scalaBinaryVersion) {
        this.scalaBinaryVersion = scalaBinaryVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getJarVersion() {
        return jarVersion;
    }

    public void setJarVersion(String jarVersion) {
        this.jarVersion = jarVersion;
    }

    public boolean isJavaTemplate() {
        return isJavaTemplate;
    }

    public void setJavaTemplate(boolean javaTemplate) {
        isJavaTemplate = javaTemplate;
    }

    public String getRunScriptName() {
        return runScriptName;
    }

    public void setRunScriptName(String runScriptName) {
        this.runScriptName = runScriptName;
    }

    public String getRunScriptPath() {
        return runScriptPath;
    }

    public void setRunScriptPath(String runScriptPath) {
        this.runScriptPath = runScriptPath;
    }

    public String getReadMePath() {
        return readMePath;
    }

    public void setReadMePath(String readMePath) {
        this.readMePath = readMePath;
    }

    public String getJarDeployPath() {
        return jarDeployPath;
    }

    public void setJarDeployPath(String jarDeployPath) {
        this.jarDeployPath = jarDeployPath;
    }

    public String getDeployScriptPath() {
        return deployScriptPath;
    }

    public void setDeployScriptPath(String deployScriptPath) {
        this.deployScriptPath = deployScriptPath;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    @Override
    public String toString() {
        return "ProjectInfoBean{" +
                "projectName='" + projectName + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", scalaVersion='" + scalaVersion + '\'' +
                ", scalaBinaryVersion='" + scalaBinaryVersion + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", javaClassName='" + javaClassName + '\'' +
                ", fullClassName='" + fullClassName + '\'' +
                ", jarName='" + jarName + '\'' +
                ", jarPath='" + jarPath + '\'' +
                ", jarVersion='" + jarVersion + '\'' +
                ", isJavaTemplate=" + isJavaTemplate +
                ", runScriptName='" + runScriptName + '\'' +
                ", runScriptPath='" + runScriptPath + '\'' +
                ", readMePath='" + readMePath + '\'' +
                ", jarDeployPath='" + jarDeployPath + '\'' +
                ", deployScriptPath='" + deployScriptPath + '\'' +
                ", templateType=" + templateType +
                '}';
    }
}
