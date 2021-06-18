package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.util.TemplateType;

import java.io.File;
import java.util.Properties;
import static com.ranga.spark.project.template.util.AppConstants.README_FILE;

public class ProjectBuilder {

    private final String appName;
    private final String targetDir;
    private final String delimiter;
    private final String integration;
    private String projectName;
    private String projectTargetPath;
    private String packageName;
    private String packageDir;
    private String className;
    private String javaClassName;
    private String fullClassName;
    private String runScriptName;
    private String runScriptPath;
    private String readMePath;
    private String jarName;
    private String jarPath;
    private String jarVersion;
    private String pomPath;
    private String jarDeployPath;
    private String deployScriptPath;
    private final Properties properties;
    private boolean isJavaTemplate;
    private TemplateType templateType;

    public ProjectBuilder(String applicationName, Properties pr) {
        properties = pr;
        appName = applicationName;
        targetDir = getPropertyValue("projectDir", System.getProperty("user.home"));
        integration = getPropertyValue("appExtension", "Integration" );
        delimiter = getPropertyValue("delimiter", "-" );

        String scalaVersion = getPropertyValue("scalaVersion", "2.12.10");
        String scalaBinaryVersion = scalaVersion.substring(0, scalaVersion.lastIndexOf("."));
        properties.setProperty("scalaVersion", scalaVersion);
        properties.setProperty("scalaBinaryVersion", scalaBinaryVersion);
        properties.setProperty("sparkVersion", getPropertyValue("sparkVersion", "3.1.1"));
        properties.setProperty("javaVersion", getPropertyValue("javaVersion", "1.8"));
        properties.setProperty("sparkScope", getPropertyValue("sparkScope", "compile"));
    }

    public void setJavaTemplate(boolean javaTemplate) {
        isJavaTemplate = javaTemplate;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    private String getPropertyValue(String key, String defaultValue) {
        return (String) properties.getOrDefault(key, defaultValue );
    }

    public static ProjectBuilder build(String projectName, Properties prop) {
        String projName = projectName;
        String templateTypeName = "default";
        if(projectName.contains("#")) {
            String split[] = projectName.split("#");
            projName = split[0];
            templateTypeName = split[1];
        }

        ProjectBuilder projectBuilder = new ProjectBuilder(projName, prop);
        projectBuilder.buildProjectInfo();
        projectBuilder.buildRunScriptAndClassInfo();
        projectBuilder.buildReadMeInfo();
        TemplateBuilder.buildTemplate(templateTypeName, projectBuilder);
        return projectBuilder;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public boolean isJavaTemplate() {
        return isJavaTemplate;
    }

    public String getDeployScriptPath() {
        return deployScriptPath;
    }

    public String getJarVersion() {
        return jarVersion;
    }

    public String getPomPath() {
        return pomPath;
    }

    public String getJarDeployPath() {
        return jarDeployPath;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectTargetPath() {
        return projectTargetPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getRunScriptName() {
        return runScriptName;
    }

    public String getRunScriptPath() {
        return runScriptPath;
    }

    public String getPOMPath() {
        return pomPath;
    }

    public String getReadMePath() {
        return readMePath;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public String getJarName() {
        return jarName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public String getAppName() {
        return appName;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getPackageDir() {
        return packageDir;
    }

    public void buildReadMeInfo() {
        this.readMePath = projectTargetPath + File.separator + README_FILE;
    }

    public void buildRunScriptAndClassInfo() {
        this.packageName = getPackage();
        this.packageDir = packageName.replace(".", "/");
        this.fullClassName = packageName + "." + className;
        String jarVersion = getPropertyValue( "jarVersion", "1.0.0-SNAPSHOT");
        this.jarVersion = jarVersion;
        this.jarName = projectName+"-"+jarVersion+".jar";
        String baseDeployJarPath = getBaseDeployJarPath();
        this.jarDeployPath = baseDeployJarPath + projectName;
        this.jarPath = baseDeployJarPath + projectName + File.separator + jarName;
        this.runScriptName = "run_"+ projectName.replace(delimiter, "_") + "_app.sh";
        this.deployScriptPath = jarDeployPath + File.separator + runScriptName;
        this.runScriptPath = projectTargetPath + File.separator + runScriptName;
    }

    private String getBaseDeployJarPath() {
        String baseDeployJarPath = getPropertyValue("baseDeployJarPath", "/apps/spark/");
        if(!baseDeployJarPath.endsWith("/")) {
            baseDeployJarPath = baseDeployJarPath +"/";
        }
        return baseDeployJarPath;
    }

    private String getPackage() {
        String basePackageName = getPropertyValue("basePackageName", "com.ranga");
        if(!basePackageName.endsWith(".")) {
            basePackageName = basePackageName +".";
        }
        return basePackageName + projectName.replace(delimiter + "integration", "")
                .replace(delimiter, ".");
    }

    private void buildProjectInfo() {
        String appNameStr = appName;
        if (!appNameStr.contains(integration)) {
            appNameStr = appNameStr + integration;
        }
        StringBuilder projectNameSB = new StringBuilder();
        StringBuilder classNameSB = new StringBuilder();
        for (int i = 0; i < appNameStr.length(); i++) {
            if(Character.isUpperCase(appNameStr.charAt(i))) {
                if (i != 0) {
                    projectNameSB.append(delimiter);
                }
                projectNameSB.append(Character.toLowerCase(appNameStr.charAt(i)));
            } else {
                projectNameSB.append(appNameStr.charAt(i));
                if(i == 0) {
                    classNameSB.append(Character.toUpperCase(appNameStr.charAt(i)));
                    continue;
                }
            }
            classNameSB.append(appNameStr.charAt(i));
        }

        projectName =  projectNameSB.toString();
        className = classNameSB+"App";
        javaClassName = classNameSB+"JavaApp";
        projectTargetPath = targetDir + File.separator + projectName;
        pomPath = projectTargetPath + File.separator + "pom.xml";
    }
}