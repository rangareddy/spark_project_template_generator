package com.ranga.spark.project.template;

import com.ranga.spark.project.template.bean.MavenBuildToolBean;
import com.ranga.spark.project.template.bean.ProjectConfig;
import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.bean.SbtBuildToolBean;
import com.ranga.spark.project.template.builder.ProjectBuilders;
import com.ranga.spark.project.template.util.FileUtil;
import com.ranga.spark.project.template.util.GenerateTemplateUtil;
import com.ranga.spark.project.template.util.TemplateType;
import com.ranga.spark.project.template.util.YamlUtil;

import java.io.File;
import java.util.List;

public class SparkProjectTemplateGenerator {

    public static void main(String[] args) {
        ProjectConfig projectConfig = YamlUtil.loadYamlFile(args);
        List<ProjectInfoBean> projectInfoBeanList = ProjectBuilders.buildProjects(projectConfig);
        for (ProjectInfoBean projectInfoBean : projectInfoBeanList) {
            createProjectTemplate(projectInfoBean);
        }
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
        GenerateTemplateUtil.generateTemplate(scalaFilePath, projectInfoBean, "scala_app_class_template.ftl", true);

        // Scala Employee Generator
        if (projectInfoBean.isCreateScalaBeanClass()) {
            String scalaEmployeeFilePath = scalaMain + File.separator + packageName + File.separator + "Employee.scala";
            GenerateTemplateUtil.generateTemplate(scalaEmployeeFilePath, projectInfoBean, "scala_employee_class_template.ftl", true);
        }

        // Scala Test App Generator
        if (projectInfoBean.getTemplateType() == TemplateType.DEFAULT) {
            String scalaTestFilePath = testScalaPath + File.separator + packageName + File.separator + className + "Test.scala";
            GenerateTemplateUtil.generateTemplate(scalaTestFilePath, projectInfoBean, "scala_app_class_test_template.ftl", true);
        }

        // Java App Generator
        if (projectInfoBean.isJavaTemplate()) {
            String javaFilePath = javaMain + File.separator + packageName + File.separator + projectInfoBean.getJavaClassName() + ".java";
            GenerateTemplateUtil.generateTemplate(javaFilePath, projectInfoBean, "java_app_class_template.ftl", true);

            // Java Employee Generator
            if (projectInfoBean.isCreateJavaBeanClass()) {
                String employeeFilePath = javaMain + File.separator + packageName + File.separator + "EmployeeBean.java";
                GenerateTemplateUtil.generateTemplate(employeeFilePath, projectInfoBean, "java_employee_class_template.ftl");
            }
        }

        // log4j
        String log4jPath = resourcesMain + File.separator + "log4j.properties";
        GenerateTemplateUtil.generateTemplate(log4jPath, projectInfoBean, "log4j.ftl", true);

        // .gitignore
        String gitIgnorePath = projectDirectory + File.separator + ".gitignore";
        GenerateTemplateUtil.generateTemplate(gitIgnorePath, projectInfoBean, "gitignore.ftl");
        MavenBuildToolBean mavenBuildToolBean = projectInfoBean.getMavenBuildToolBean();
        if (mavenBuildToolBean != null) {
            // pom file
            String pomFile = projectDirectory + File.separator + mavenBuildToolBean.getPomFile();
            GenerateTemplateUtil.generateTemplate(pomFile, projectInfoBean, "pom.ftl");
        }

        if (projectInfoBean.getSbtBuildToolBean() != null) {
            // build.sbt
            SbtBuildToolBean sbtBuildToolBean = projectInfoBean.getSbtBuildToolBean();
            String buildSbtPath = projectDirectory + File.separator + sbtBuildToolBean.getBuildSbtName();
            GenerateTemplateUtil.generateTemplate(buildSbtPath, projectInfoBean, "build.sbt.ftl");

            String projectDirPath = projectDirectory + File.separator + "project";
            String buildPropertiesPath = projectDirPath + File.separator + "build.properties";
            GenerateTemplateUtil.generateTemplate(buildPropertiesPath, projectInfoBean, "build.properties.ftl", true);
            String pluginsSbtPath = projectDirPath + File.separator + "plugins.sbt";
            GenerateTemplateUtil.generateTemplate(pluginsSbtPath, projectInfoBean, "plugins.sbt.ftl");
        }

        // run script
        GenerateTemplateUtil.generateTemplate(projectInfoBean.getRunScriptPath(), projectInfoBean, "run_script.ftl");

        // README.md
        GenerateTemplateUtil.generateTemplate(projectInfoBean.getReadMePath(), projectInfoBean, "README.ftl");

        System.out.println("Application <"+projectInfoBean.getProjectName()+"> created successfully.");
    }
}