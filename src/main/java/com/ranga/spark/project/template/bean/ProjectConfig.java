package com.ranga.spark.project.template.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class ProjectConfig implements Serializable {

    private List<ProjectDetailBean> projectDetails;
    private String baseProjectDir = System.getProperty("user.home");
    private String basePackageName = "com.ranga";
    private String baseDeployJarPath = "/apps/spark/";
    private String buildTools = "maven";
    private String jarVersion = "1.0.0-SNAPSHOT";
    private String scalaVersion = "2.12.10";
    private String scalaBinaryVersion = "2.12";
    private String javaVersion = "1.8";
    private String sparkVersion = "3.1.1";
    private String sparkScope = "compile";
    private String hwcVersion = "2.4.0.7.1.6.0-297";
    private String hwcScope = "compile";
    private String hbaseSparkVersion = "";
    private String hbaseSparkScope = "";
    private String sbtVersion = "1.4.7";
    private String secureCluster = "false";
    private List<DependencyBean> defaultTemplate;
    private List<DependencyBean> hiveTemplate;
    private List<DependencyBean> hbaseTemplate;
    private List<DependencyBean> hwcTemplate;
    private List<DependencyBean> fileFormatsTemplate;

    // IIB
    {
        hiveTemplate = hbaseTemplate = hwcTemplate = fileFormatsTemplate =  Collections.emptyList();
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

    public String getSparkVersion() {
        return sparkVersion;
    }

    public void setSparkVersion(String sparkVersion) {
        this.sparkVersion = sparkVersion;
    }

    public String getSparkScope() {
        return sparkScope;
    }

    public void setSparkScope(String sparkScope) {
        this.sparkScope = sparkScope;
    }

    public String getHwcVersion() {
        return hwcVersion;
    }

    public void setHwcVersion(String hwcVersion) {
        this.hwcVersion = hwcVersion;
    }

    public String getHwcScope() {
        return hwcScope;
    }

    public void setHwcScope(String hwcScope) {
        this.hwcScope = hwcScope;
    }

    public String getHbaseSparkVersion() {
        return hbaseSparkVersion;
    }

    public void setHbaseSparkVersion(String hbaseSparkVersion) {
        this.hbaseSparkVersion = hbaseSparkVersion;
    }

    public String getHbaseSparkScope() {
        return hbaseSparkScope;
    }

    public void setHbaseSparkScope(String hbaseSparkScope) {
        this.hbaseSparkScope = hbaseSparkScope;
    }

    public List<DependencyBean> getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(List<DependencyBean> defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }

    public List<DependencyBean> getHiveTemplate() {
        return hiveTemplate;
    }

    public void setHiveTemplate(List<DependencyBean> hiveTemplate) {
        this.hiveTemplate = hiveTemplate;
    }

    public List<ProjectDetailBean> getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(List<ProjectDetailBean> projectDetails) {
        this.projectDetails = projectDetails;
    }

    public List<DependencyBean> getHbaseTemplate() {
        return hbaseTemplate;
    }

    public void setHbaseTemplate(List<DependencyBean> hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    public List<DependencyBean> getHwcTemplate() {
        return hwcTemplate;
    }

    public void setHwcTemplate(List<DependencyBean> hwcTemplate) {
        this.hwcTemplate = hwcTemplate;
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

    public String getSbtVersion() {
        return sbtVersion;
    }

    public void setSbtVersion(String sbtVersion) {
        this.sbtVersion = sbtVersion;
    }

    public String getBaseDeployJarPath() {
        if (!baseDeployJarPath.endsWith("/")) {
            baseDeployJarPath = baseDeployJarPath + "/";
        }
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

    public String getSecureCluster() {
        return secureCluster;
    }

    public void setSecureCluster(String secureCluster) {
        this.secureCluster = secureCluster;
    }

    public List<DependencyBean> getFileFormatsTemplate() {
        return fileFormatsTemplate;
    }

    public void setFileFormatsTemplate(List<DependencyBean> fileFormatsTemplate) {
        this.fileFormatsTemplate = fileFormatsTemplate;
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
                ", sparkVersion='" + sparkVersion + '\'' +
                ", sparkScope='" + sparkScope + '\'' +
                ", hwcVersion='" + hwcVersion + '\'' +
                ", hwcScope='" + hwcScope + '\'' +
                ", hbaseSparkVersion='" + hbaseSparkVersion + '\'' +
                ", hbaseSparkScope='" + hbaseSparkScope + '\'' +
                ", sbtVersion='" + sbtVersion + '\'' +
                ", secureCluster='" + secureCluster + '\'' +
                ", defaultTemplate=" + defaultTemplate +
                ", hiveTemplate=" + hiveTemplate +
                ", hbaseTemplate=" + hbaseTemplate +
                ", hwcTemplate=" + hwcTemplate +
                ", fileFormatsTemplate=" + fileFormatsTemplate +
                '}';
    }
}
