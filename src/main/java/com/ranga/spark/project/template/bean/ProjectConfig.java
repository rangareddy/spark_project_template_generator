package com.ranga.spark.project.template.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String authorEmail = "";
    private List<ProjectDetailBean> projectDetails;
    private Map<String, List<Map>> templates;
    private List<ComponentDetailBean> componentVersions;
    private String scope = "compile";
}