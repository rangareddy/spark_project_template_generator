package com.ranga.spark.project.template;

import java.io.File;
import java.util.*;

import com.ranga.spark.project.template.builder.ProjectBuilder;
import com.ranga.spark.project.template.util.GenerateTemplate;
import com.ranga.spark.project.template.util.PropertyUtil;

public class SparkProjectTemplateGenerator {

    private static ProjectBuilder projectBuilder;

    public static void main(String[] args) throws Exception {
        Properties properties = PropertyUtil.loadProperties(args);
        String appName = (String) properties.getOrDefault("appName", "SparkWordCount");
        String projects[] = appName.split(",");

        for(String projectName : projects) {
            System.out.println("========================");
            projectBuilder = ProjectBuilder.build(projectName, properties);
            File projectTargetFile = new File(projectBuilder.getProjectTargetPath());
            createProject(projectTargetFile);
            createReadMeFile();
            createRunScriptFile();
            createPOMFile();
            createSparkFiles();
            System.out.println("========================\n");
        }
    }

    private static void createSparkFiles() {
        String className = projectBuilder.getClassName();
        String projectTargetPath = projectBuilder.getProjectTargetPath();
        String packageName = projectBuilder.getPackageName().replace(".", "/");
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
        File scalaFile = new File( scalaFilePath);
        new File(scalaFile.getParentFile().getAbsolutePath()).mkdirs();
        GenerateTemplate.generateTemplate(projectBuilder, "scala_app_class.ftl", scalaFile);
        System.out.println(scalaFile+" created successfully");

        // Scala Test App Generator
        String scalaTestFilePath = testScalaPath + File.separator + packageName + File.separator + className +"Test.scala";
        File scalaTestFile = new File(scalaTestFilePath);
        new File(scalaTestFile.getParentFile().getAbsolutePath()).mkdirs();
        GenerateTemplate.generateTemplate(projectBuilder, "scala_app_class_test.ftl", scalaTestFile);
        System.out.println(scalaTestFile+" created successfully");

        // Java App Generator
        String javaFilePath = javaMain + File.separator + packageName + File.separator + projectBuilder.getJavaClassName() + ".java";
        File javaFile = new File(javaFilePath);
        new File(javaFile.getParentFile().getAbsolutePath()).mkdirs();
        GenerateTemplate.generateTemplate(projectBuilder, "java_app_class.ftl", javaFile);
        System.out.println(javaFile+" created successfully");

        // log4j
        String log4jFile = resourcesMain + File.separator + "log4j.properties";
        File log4j = new File(log4jFile);
        new File(log4j.getParentFile().getAbsolutePath()).mkdirs();
        GenerateTemplate.generateTemplate(projectBuilder, "log4j.ftl", log4j);
        System.out.println(log4j+" created successfully");

        // .gitignore
        String gitIgnore = projectTargetPath + File.separator + ".gitignore";
        File gitIgnoreFile = new File(gitIgnore);
        GenerateTemplate.generateTemplate(projectBuilder, "gitignore.ftl", gitIgnoreFile);
        System.out.println(gitIgnoreFile+" created successfully");
    }

    private static void createPOMFile() {
        File pomFile = new File(projectBuilder.getPOMPath());
        GenerateTemplate.generateTemplate(projectBuilder, "pom.ftl", pomFile);
        System.out.println(pomFile+" created successfully");
    }

    private static void createRunScriptFile()  {
        File runScriptFile = new File(projectBuilder.getRunScriptPath());
        GenerateTemplate.generateTemplate(projectBuilder, "run_script.ftl", runScriptFile);
        System.out.println(runScriptFile +" created successfully");
    }

    private static void createReadMeFile() {
        File readMeFile = new File(projectBuilder.getReadMePath());
        GenerateTemplate.generateTemplate(projectBuilder, "README.ftl", readMeFile);
        System.out.println(readMeFile+" created successfully");
    }

    private static void createProject(File projectTargetFile) {
        if (projectTargetFile.exists()) {
            deleteProject(projectTargetFile);
        }
        projectTargetFile.mkdirs();
        System.out.println(projectTargetFile +" created successfully");
    }

    public static void deleteProject(File dir) {
        File[] files = dir.listFiles();
        if(files != null) {
            for (final File file : files) {
                deleteProject(file);
            }
        }
        dir.delete();
    }
}
