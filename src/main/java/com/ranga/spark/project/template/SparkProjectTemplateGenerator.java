package com.ranga.spark.project.template;

import com.ranga.spark.project.template.bean.MavenBuildToolBean;
import com.ranga.spark.project.template.bean.ProjectConfig;
import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.bean.SbtBuildToolBean;
import com.ranga.spark.project.template.builder.ProjectBuilders;
import com.ranga.spark.project.template.util.*;
import java.io.File;
import java.util.List;

public class SparkProjectTemplateGenerator {

    public static void main(String[] args) throws Exception {
        ProjectConfig projectConfig = YamlUtil.loadYamlFile();
        List<ProjectInfoBean> projectInfoBeanList = ProjectBuilders.buildProjects(projectConfig);
        for(ProjectInfoBean projectInfoBean : projectInfoBeanList) {
            createProjectTemplate(projectInfoBean);
        }

        /*for (String projectName : projects) {
            System.out.println("========================");
            ProjectBuilder projectBuilder = ProjectBuilder.build(projectName, properties);
            createProjectTemplate(projectBuilder);
            System.out.println("========================\n");
        }*/
    }

    private static void createProjectTemplate(ProjectInfoBean projectInfoBean) {
        String projectDirectory = projectInfoBean.getProjectDirectory();
        File projectTargetFile = new File(projectDirectory);
        FileUtil.createDir(projectTargetFile);
        String className = projectInfoBean.getClassName();
        String packageName = projectInfoBean.getPackageDir();
        String srcMain = "src/main";
        String testMain = "src/test";
        String packagePath = projectDirectory + File.separator + srcMain;
        String javaMain = packagePath + File.separator + "java";
        String scalaMain = packagePath + File.separator + "scala";
        String testPackagePath = projectDirectory + File.separator + testMain;
        String testScalaPath = testPackagePath + File.separator + "scala";
        String resourcesMain = packagePath + File.separator + "resources";

        // Scala App Generator
        String scalaFilePath = scalaMain + File.separator + packageName + File.separator + className + ".scala";
        generateTemplate(scalaFilePath, projectInfoBean, "scala_app_class_template.ftl", true);

        // Scala Test App Generator
        if (projectInfoBean.getTemplateType() == TemplateType.DEFAULT) {
            String scalaTestFilePath = testScalaPath + File.separator + packageName + File.separator + className + "Test.scala";
            generateTemplate(scalaTestFilePath, projectInfoBean, "scala_app_class_test.ftl", true);
        }
        // run script
        generateTemplate(projectInfoBean.getRunScriptPath().replace("run_", "run_sec_"), projectInfoBean, "run_sec_script.ftl");

        // Java App Generator
        if (projectInfoBean.isJavaTemplate()) {
            String javaFilePath = javaMain + File.separator + packageName + File.separator + projectInfoBean.getJavaClassName() + ".java";
            generateTemplate(javaFilePath, projectInfoBean, "java_app_class_template.ftl", true);
            String employeeFilePath = javaMain + File.separator + packageName + File.separator + "EmployeeBean.java";
            generateTemplate(employeeFilePath, projectInfoBean, "employee.ftl");
        }

        // log4j
        String log4jPath = resourcesMain + File.separator + "log4j.properties";
        generateTemplate(log4jPath, projectInfoBean, "log4j.ftl", true);

        // .gitignore
        String gitIgnorePath = projectDirectory + File.separator + ".gitignore";
        generateTemplate(gitIgnorePath, projectInfoBean, "gitignore.ftl");
        MavenBuildToolBean mavenBuildToolBean = projectInfoBean.getMavenBuildToolBean();
        if (mavenBuildToolBean != null) {
            // pom file
            String pomFile = projectDirectory + File.separator + mavenBuildToolBean.getPomFile();
            generateTemplate(pomFile, projectInfoBean, "pom.ftl");
        }

        if (projectInfoBean.getSbtBuildToolBean() != null) {
            // build.sbt
            SbtBuildToolBean sbtBuildToolBean = projectInfoBean.getSbtBuildToolBean();
            generateTemplate(sbtBuildToolBean.getBuildSbtName(), projectInfoBean, "build.sbt.ftl");
            String projectDirPath = projectDirectory + File.separator + "project";
            String buildPropertiesPath = projectDirPath + File.separator + "build.properties";
            generateTemplate(buildPropertiesPath, projectInfoBean, "build.properties.ftl", true);
            String pluginsSbtPath = projectDirPath + File.separator + "plugins.sbt";
            generateTemplate(pluginsSbtPath, projectInfoBean, "plugins.sbt.ftl");
        }

        // run script
        generateTemplate(projectInfoBean.getRunScriptPath(), projectInfoBean, "run_script.ftl");

        // README.md
        generateTemplate(projectInfoBean.getReadMePath(), projectInfoBean, "README.ftl");
    }

    /*private static void createProjectTemplate(ProjectBuilder projectBuilder) {
        File projectTargetFile = new File(projectBuilder.getProjectTargetPath());
        FileUtil.createDir(projectTargetFile);
        String className = projectBuilder.getClassName();
        String projectTargetPath = projectBuilder.getProjectTargetPath();
        String packageName = projectBuilder.getPackageDir();
        String srcMain = "src/main";
        String testMain = "src/test";
        String packagePath = projectTargetPath + File.separator + srcMain;
        String javaMain = packagePath + File.separator + "java";
        String scalaMain = packagePath + File.separator + "scala";
        String testPackagePath = projectTargetPath + File.separator + testMain;
        String testScalaPath = testPackagePath + File.separator + "scala";
        String resourcesMain = packagePath + File.separator + "resources";

        // Scala App Generator
        String scalaFilePath = scalaMain + File.separator + packageName + File.separator + className + ".scala";
        generateTemplate(scalaFilePath, projectBuilder, "scala_app_class_template.ftl", true);

        // Scala Test App Generator
        if (projectBuilder.getTemplateType() == TemplateType.DEFAULT) {
            String scalaTestFilePath = testScalaPath + File.separator + packageName + File.separator + className + "Test.scala";
            generateTemplate(scalaTestFilePath, projectBuilder, "scala_app_class_test.ftl", true);
        } else {
            // run script
            generateTemplate(projectBuilder.getRunScriptPath().replace("run_", "run_sec_"), projectBuilder, "run_sec_script.ftl");
        }

        // Java App Generator
        if (projectBuilder.isJavaTemplate()) {
            String javaFilePath = javaMain + File.separator + packageName + File.separator + projectBuilder.getJavaClassName() + ".java";
            generateTemplate(javaFilePath, projectBuilder, "java_app_class_template.ftl", true);
            String employeeFilePath = javaMain + File.separator + packageName + File.separator + "EmployeeBean.java";
            generateTemplate(employeeFilePath, projectBuilder, "employee.ftl");
        }

        // log4j
        String log4jPath = resourcesMain + File.separator + "log4j.properties";
        generateTemplate(log4jPath, projectBuilder, "log4j.ftl", true);

        // .gitignore
        String gitIgnorePath = projectTargetPath + File.separator + ".gitignore";
        generateTemplate(gitIgnorePath, projectBuilder, "gitignore.ftl");

        String[] buildTools = projectBuilder.getBuildTools();
        for (String buildTool : buildTools) {
            buildTool = buildTool.toLowerCase();
            if ("maven".equals(buildTool) || "mvn".equals(buildTool)) {
                // pom file
                generateTemplate(projectBuilder.getPOMPath(), projectBuilder, "pom.ftl");
            } else if ("sbt".equals(buildTool)) {
                // build.sbt
                generateTemplate(projectBuilder.getBuildSbtPath(), projectBuilder, "build.sbt.ftl");
                String projectDirPath = projectTargetPath + File.separator + "project";
                String buildPropertiesPath = projectDirPath + File.separator + "build.properties";
                generateTemplate(buildPropertiesPath, projectBuilder, "build.properties.ftl", true);
                String pluginsSbtPath = projectDirPath + File.separator + "plugins.sbt";
                generateTemplate(pluginsSbtPath, projectBuilder, "plugins.sbt.ftl");
            }
        }

        // run script
        generateTemplate(projectBuilder.getRunScriptPath(), projectBuilder, "run_script.ftl");

        // README.md
        generateTemplate(projectBuilder.getReadMePath(), projectBuilder, "README.ftl");
    }

    private static void generateTemplate(String filePath, ProjectBuilder projectBuilder, String ftlFile) {
        generateTemplate(filePath, projectBuilder, ftlFile, false);
    }
    */

    private static void generateTemplate(String filePath, ProjectInfoBean projectInfoBean, String ftlFile) {
        generateTemplate(filePath, projectInfoBean, ftlFile, false);
    }

    private static void generateTemplate(String filePath, Object templateData, String ftlFile, boolean isCreateDir) {
        File templateFile = new File(filePath);
        if (isCreateDir) {
            boolean isCreated = templateFile.getParentFile().mkdirs();
            if (isCreated) {
                System.out.println(templateFile.getParentFile().getAbsolutePath() + " created.");
            }
        }
        GenerateTemplateUtil.generateTemplate(templateData, ftlFile, templateFile);
        System.out.println(templateFile + " created successfully");
    }
}