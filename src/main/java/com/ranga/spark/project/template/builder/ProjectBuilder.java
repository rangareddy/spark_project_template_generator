package com.ranga.spark.project.template.builder;

import java.io.File;
import java.util.Properties;

public class ProjectBuilder {

    private String projectName;
    private final String appName;
    private String projectTargetPath;
    private String packageName;
    private String className;
    private String javaClassName;
    private String fullClassName;
    private String runScriptName;
    private String runScriptPath;
    private final static String readMeName = "README.md";
    private String readMePath;
    private String targetDir;
    private static final String INTEGRATION = "Integration";
    private static final String DELIMITER = "-";
    private String jarName;
    private String jarPath;
    private String jarVersion;
    private static Properties pr;
    private String pomPath;
    private String jarDeployPath;
    private String deployScriptPath;
    private final Properties properties;

    public ProjectBuilder(String applicationName) {
        properties = pr;
        appName = applicationName;
        targetDir = pr.getProperty("targetDir");
        if(targetDir == null || targetDir.trim().isEmpty()) {
            targetDir =  System.getProperty("user.home");
        }
    }

    public static ProjectBuilder build(String projectName, Properties prop) {
        pr = prop;
        ProjectBuilder projectBuilder = new ProjectBuilder(projectName);
        projectBuilder.buildProjectInfo();
        projectBuilder.buildRunScriptAndClassInfo();
        projectBuilder.buildReadMeInfo();
        return projectBuilder;
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

    public String getReadMeName() {
        return readMeName;
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

    public void buildReadMeInfo() {
        this.readMePath = projectTargetPath + File.separator + readMeName;
    }

    public void buildRunScriptAndClassInfo() {
        String basePackageName = (String) properties.getOrDefault("basePackageName", "com.ranga");
        if(!basePackageName.endsWith(".")) {
            basePackageName = basePackageName +".";
        }
        this.packageName = basePackageName + projectName.replace(DELIMITER + "integration", "").replace(DELIMITER, ".");
        this.fullClassName = packageName + "." + className;
        String jarVersion = (String) properties.getOrDefault("jarVersion", "1.0.0-SNAPSHOT");
        this.jarVersion = jarVersion;
        this.jarName = projectName+"-"+jarVersion+".jar";
        String baseDeployJarPath = (String) properties.getOrDefault("baseDeployJarPath", "/apps/spark/");
        if(!baseDeployJarPath.endsWith("/")) {
            baseDeployJarPath = baseDeployJarPath +"/";
        }
        this.jarDeployPath = baseDeployJarPath + projectName;
        this.jarPath = baseDeployJarPath + projectName + File.separator + jarName;
        this.runScriptName = "run_"+ projectName.replace(DELIMITER, "_") + "_app.sh";
        this.deployScriptPath = jarDeployPath + File.separator + runScriptName;
        this.runScriptPath = projectTargetPath + File.separator + runScriptName;
    }

    private void buildProjectInfo() {
        String appNameStr = appName;
        if (!appNameStr.contains(INTEGRATION)) {
            appNameStr = appNameStr + INTEGRATION;
        }
        StringBuilder projectNameSB = new StringBuilder();
        StringBuilder classNameSB = new StringBuilder();
        for (int i = 0; i < appNameStr.length(); i++) {
            if(Character.isUpperCase(appNameStr.charAt(i))) {
                if (i != 0) {
                    projectNameSB.append(DELIMITER);
                }
                projectNameSB.append(Character.toLowerCase(appNameStr.charAt(i)));
                classNameSB.append(appNameStr.charAt(i));
            } else {
                projectNameSB.append(appNameStr.charAt(i));
                if(i == 0) {
                    classNameSB.append(Character.toUpperCase(appNameStr.charAt(i)));
                    continue;
                }
                classNameSB.append(appNameStr.charAt(i));
            }
        }

        projectName =  projectNameSB.toString();
        className = classNameSB.toString()+"App";
        javaClassName = classNameSB.toString()+"JavaApp";
        projectTargetPath = targetDir + File.separator + projectName;
        pomPath = projectTargetPath + File.separator + "pom.xml";
    }
}