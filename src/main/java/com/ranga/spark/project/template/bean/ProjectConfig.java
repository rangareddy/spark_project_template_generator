package com.ranga.spark.project.template.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ProjectConfig implements Serializable {

    private String baseProjectDir = System.getProperty("user.home");
    private String basePackageName = "com.ranga";
    private String baseDeployJarPath = "/apps/spark/";
    private String buildTools = "maven";
    private String jarVersion = "1.0.0-SNAPSHOT";
    private String scalaVersion = "2.12.10";
    private String scalaBinaryVersion = "2.12";
    private String javaVersion = "1.8";
    private String sbtVersion = "1.4.7";
    private String secureCluster = "false";
    private String sslCluster = "false";
    private String author = "Ranga Reddy";
    private List<ProjectDetailBean> projectDetails;
    private Map<String, List<Map>> templates;
    private List<ComponentDetailBean> componentVersions;
    private String scope = "compile";

    public ProjectConfig() {
    }

    public List<ProjectDetailBean> getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(List<ProjectDetailBean> projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getBaseProjectDir() {
        return baseProjectDir;
    }

    public void setBaseProjectDir(String baseProjectDir) {
        this.baseProjectDir = baseProjectDir;
    }

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getBaseDeployJarPath() {
        return baseDeployJarPath;
    }

    public void setBaseDeployJarPath(String baseDeployJarPath) {
        this.baseDeployJarPath = baseDeployJarPath;
    }

    public String getBuildTools() {
        return buildTools;
    }

    public void setBuildTools(String buildTools) {
        this.buildTools = buildTools;
    }

    public String getJarVersion() {
        return jarVersion;
    }

    public void setJarVersion(String jarVersion) {
        this.jarVersion = jarVersion;
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

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getSbtVersion() {
        return sbtVersion;
    }

    public void setSbtVersion(String sbtVersion) {
        this.sbtVersion = sbtVersion;
    }

    public String getSecureCluster() {
        return secureCluster;
    }

    public void setSecureCluster(String secureCluster) {
        this.secureCluster = secureCluster;
    }

    public String getSslCluster() {
        return sslCluster;
    }

    public void setSslCluster(String sslCluster) {
        this.sslCluster = sslCluster;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<String, List<Map>> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, List<Map>> templates) {
        this.templates = templates;
    }

    public List<ComponentDetailBean> getComponentVersions() {
        return componentVersions;
    }

    public void setComponentVersions(List<ComponentDetailBean> componentVersions) {
        this.componentVersions = componentVersions;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "ProjectConfig{" +
                "projectDetails=" + projectDetails +
                ", baseProjectDir='" + baseProjectDir + '\'' +
                ", basePackageName='" + basePackageName + '\'' +
                ", baseDeployJarPath='" + baseDeployJarPath + '\'' +
                ", buildTools='" + buildTools + '\'' +
                ", jarVersion='" + jarVersion + '\'' +
                ", scalaVersion='" + scalaVersion + '\'' +
                ", scalaBinaryVersion='" + scalaBinaryVersion + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", sbtVersion='" + sbtVersion + '\'' +
                ", secureCluster='" + secureCluster + '\'' +
                ", sslCluster='" + sslCluster + '\'' +
                ", author='" + author + '\'' +
                ", templates=" + templates +
                ", componentVersions=" + componentVersions +
                ", scope='" + scope + '\'' +
                '}';
    }
}