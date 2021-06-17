package com.ranga.spark.project.template;

import java.io.File;
import java.util.*;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.scala.HelloWorldTemplate;
import com.ranga.spark.project.template.builder.ProjectBuilder;
import com.ranga.spark.project.template.util.FileUtil;
import com.ranga.spark.project.template.util.GenerateTemplateUtil;
import com.ranga.spark.project.template.util.PropertyUtil;

public class SparkProjectTemplateGenerator {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertyUtil.loadProperties(args);
        String appName = (String) properties.getOrDefault("appName", "SparkWordCount");
        String[] projects = appName.split(",");
        for(String projectName : projects) {
            System.out.println("========================");
            ProjectBuilder projectBuilder = ProjectBuilder.build(projectName, properties);
            createProjectTemplate(projectBuilder);
            System.out.println("========================\n");
        }
    }

    private static void createProjectTemplate(ProjectBuilder projectBuilder) {
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
        String scalaFilePath = scalaMain + File.separator + packageName + File.separator + className +".scala";
        generateTemplate(scalaFilePath, projectBuilder, "scala_app_class_template.ftl", true);

        // Scala Test App Generator
        String scalaTestFilePath = testScalaPath + File.separator + packageName + File.separator + className +"Test.scala";
        generateTemplate(scalaTestFilePath, projectBuilder, "scala_app_class_test.ftl", true);

        // Java App Generator
        String javaFilePath = javaMain + File.separator + packageName + File.separator + projectBuilder.getJavaClassName() + ".java";
        //generateTemplate(javaFilePath, projectBuilder, "java_app_class.ftl", true);
        generateTemplate(javaFilePath, projectBuilder, "java_app_class_template.ftl", true);

        // log4j
        String log4jPath = resourcesMain + File.separator + "log4j.properties";
        generateTemplate(log4jPath, projectBuilder, "log4j.ftl", true);

        // .gitignore
        String gitIgnorePath = projectTargetPath + File.separator + ".gitignore";
        generateTemplate(gitIgnorePath, projectBuilder, "gitignore.ftl");

        // pom file
        generateTemplate(projectBuilder.getPOMPath(), projectBuilder, "pom.ftl");

        // run script
        generateTemplate(projectBuilder.getRunScriptPath(), projectBuilder, "run_script.ftl");

        // README.md
        generateTemplate(projectBuilder.getReadMePath(), projectBuilder, "README.ftl");
    }

    private static  void generateTemplate(String filePath, ProjectBuilder projectBuilder, String ftlFile) {
        generateTemplate(filePath, projectBuilder, ftlFile, false);
    }

    private static  void generateTemplate(String filePath, Object templateData, String ftlFile, boolean isCreateDir) {
        File templateFile = new File(filePath);
        if(isCreateDir) {
            templateFile.getParentFile().mkdirs();
        }
        GenerateTemplateUtil.generateTemplate(templateData, ftlFile, templateFile);
        System.out.println(templateFile+" created successfully");
    }
}
