package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.java.DefaultJavaTemplate;
import com.ranga.spark.project.template.api.java.HWCJavaTemplate;
import com.ranga.spark.project.template.api.scala.DefaultTemplate;
import com.ranga.spark.project.template.api.scala.HBaseTemplate;
import com.ranga.spark.project.template.api.scala.HWCTemplate;
import com.ranga.spark.project.template.api.scala.HiveTemplate;
import com.ranga.spark.project.template.bean.*;
import com.ranga.spark.project.template.util.TemplateType;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static com.ranga.spark.project.template.util.AppConstants.README_FILE;

public class ProjectBuilders implements Serializable {
    private static ProjectConfig projectConfig;

    public static List<ProjectInfoBean> buildProjects(ProjectConfig config) {
        projectConfig = config;
        List<ProjectDetailBean> projectDetails = projectConfig.getProjectDetails();
        if (projectDetails == null || projectDetails.isEmpty()) {
            throw new RuntimeException("Project details are not specified in configuration file.");
        }
        List<ProjectInfoBean> projectInfoBeanList = new ArrayList<>(projectDetails.size());
        String scalaVersion = projectConfig.getScalaVersion();
        String scalaBinaryVersion = getScalaBinaryVersion(projectConfig.getScalaBinaryVersion(), scalaVersion);
        String baseProjectDir = projectConfig.getBaseProjectDir();
        String baseDeployJarPath = projectConfig.getBaseDeployJarPath();
        String javaVersion = projectConfig.getJavaVersion();
        for (ProjectDetailBean projectDetail : projectDetails) {
            TemplateType templateType = getTemplateType(projectDetail.getTemplateName());
            String sourceProjectName = projectDetail.getSourceProjectName();
            String projectName = getProjectName(projectDetail);
            String projectVersion = getUpdatedValue(projectDetail.getProjectVersion(), projectConfig.getJarVersion());
            String projectDir = baseProjectDir + File.separator + projectName;
            String packageName = getPackageName(projectName, projectDetail);
            String packageDir = packageName.replace(".", "/");
            String delimiter = projectDetail.getDelimiter();

            ProjectInfoBean projectInfoBean = new ProjectInfoBean();
            projectInfoBean.setProjectName(projectName);
            projectInfoBean.setSourceProjectName(sourceProjectName);
            projectInfoBean.setProjectVersion(projectVersion);
            projectInfoBean.setScalaVersion(scalaVersion);
            projectInfoBean.setJavaVersion(javaVersion);
            projectInfoBean.setScalaBinaryVersion(scalaBinaryVersion);
            projectInfoBean.setProjectDirectory(projectDir);
            String jarVersion = projectConfig.getJarVersion();
            projectInfoBean.setJarVersion(jarVersion);
            projectInfoBean.setPackageName(packageName);
            projectInfoBean.setDelimiter(delimiter);
            projectInfoBean.setPackageDir(packageDir);
            projectInfoBean.setBaseDeployJarPath(baseDeployJarPath);
            projectInfoBean.setTemplateType(templateType);
            String className = sourceProjectName +"App";
            projectInfoBean.setClassName(className);
            projectInfoBean.setJavaClassName(sourceProjectName +"JavaApp");
            projectInfoBean.setFullClassName(packageName + "." + className);
            String jarName = projectName + "-" + jarVersion + ".jar";
            projectInfoBean.setJarName(jarName);

            String jarDeployPath = baseDeployJarPath + projectName;
            projectInfoBean.setJarDeployPath(jarDeployPath);

            String jarPath = jarDeployPath + File.separator + jarName;
            projectInfoBean.setJarPath(jarPath);

            String runScriptName = "run_" + projectName.replace(delimiter, "_") + "_app.sh";
            projectInfoBean.setRunScriptName(runScriptName);

            String runScriptPath = projectDir + File.separator + runScriptName;
            projectInfoBean.setRunScriptPath(runScriptPath);

            String readMePath = projectDir + File.separator + README_FILE;
            projectInfoBean.setReadMePath(readMePath);
            String deployScriptPath = jarDeployPath + File.separator + runScriptName;
            projectInfoBean.setDeployScriptPath(deployScriptPath);
            boolean isClouderaRepo = checkClouderaRepo(projectConfig.getSparkVersion());
            String repoName = "<repository>\n" +
                    "            <id>central</id>\n" +
                    "            <name>Maven Central</name>\n" +
                    "            <url>https://repo1.maven.org/maven2</url>\n" +
                    "            <snapshots>\n" +
                    "                <enabled>false</enabled>\n" +
                    "            </snapshots>\n" +
                    "        </repository>";
            if(isClouderaRepo) {
                repoName = "<repository>\n" +
                        "            <id>cldr-repo</id>\n" +
                        "            <name>Cloudera Public Repo</name>\n" +
                        "            <url>http://repository.cloudera.com/artifactory/cloudera-repos/</url>\n" +
                        "        </repository>\n" +
                        "\n" +
                        "        <repository>\n" +
                        "            <id>hdp-repo</id>\n" +
                        "            <name>Hortonworks Public Repo</name>\n" +
                        "            <url>http://repo.hortonworks.com/content/repositories/releases/</url>\n" +
                        "        </repository>\n";

            }
            projectInfoBean.setRepoName(repoName);
            buildTemplates(projectInfoBean);
            projectInfoBeanList.add(projectInfoBean);
        }
        return projectInfoBeanList;
    }

    private static boolean checkClouderaRepo(String sparkVersion) {
        int count = 0;
        int index = 0;
        while((index = sparkVersion.indexOf(".")) != -1) {
            count++;
            sparkVersion = sparkVersion.substring(index+1);
        }
        return count > 2;
    }

    private static void buildTemplates(ProjectInfoBean projectInfoBean) {
        TemplateType templateType = projectInfoBean.getTemplateType();
        BaseTemplate template;
        BaseTemplate javaTemplate = null;
        String className = projectInfoBean.getClassName();
        String javaClassName = projectInfoBean.getJavaClassName();
        List<DependencyBean> defaultTemplateDependency = projectConfig.getDefaultTemplate();
        List<DependencyBean> othersTemplatesDependency = Collections.emptyList();
        switch (templateType) {
            case HBASE:
                template = new HBaseTemplate(className);
                othersTemplatesDependency = projectConfig.getHbaseTemplate();
                break;
            case HIVE:
                template = new HiveTemplate(className);
                othersTemplatesDependency = projectConfig.getHiveTemplate();
                break;
            case HWC:
                template = new HWCTemplate(className);
                javaTemplate = new HWCJavaTemplate(javaClassName);
                othersTemplatesDependency = projectConfig.getHwcTemplate();
                break;
            default:
                template = new DefaultTemplate(className);
                javaTemplate = new DefaultJavaTemplate(javaClassName);
        }

        projectInfoBean.setScalaCodeTemplate(getCodeTemplateBean(template));

        if (javaTemplate != null) {
            projectInfoBean.setJavaTemplate(true);
            projectInfoBean.setJavaCodeTemplate(getCodeTemplateBean(javaTemplate));
        }

        Set<DependencyBean> dependencyBeanSet = new LinkedHashSet<>(defaultTemplateDependency);
        if (!othersTemplatesDependency.isEmpty()) {
            dependencyBeanSet.addAll(othersTemplatesDependency);
        }
        buildDependencies(dependencyBeanSet, projectInfoBean);
    }

    private static void buildDependencies(Set<DependencyBean> dependencyBeanSet, ProjectInfoBean projectInfoBean) {
        DependencyBuilder dependencyBuilder = DependencyBuilder.build(dependencyBeanSet);

        Set<String> propertyVersions = dependencyBuilder.getPropertyVersions();
        List<String> prerequitiesList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(String propVersion : propertyVersions) {
            String split[] = propVersion.split("##");
            String propName = split[0];
            String propValue = split[2];
            if(propName.toLowerCase().endsWith("version") && !propName.contains("Binary")) {
                String propertyName = getPropertyName(propName);
                sb.append("* ").append(propertyName).append(":").append(propValue);
                prerequitiesList.add(propertyName +" : "+propValue);
            }
        }
        projectInfoBean.setPrerequitiesList(prerequitiesList);
        projectInfoBean.setPrerequisites(sb.toString());

        for (String buildTool : projectConfig.getBuildTools().split(",")) {
            if ("maven".equals(buildTool) || "mvn".equals(buildTool)) {
                MavenBuildToolBean mavenBuildToolBean = MavenBuildToolBean.build(dependencyBuilder);
                projectInfoBean.setMavenBuildToolBean(mavenBuildToolBean);
            } else if ("sbt".equals(buildTool)) {
                SbtBuildToolBean sbtBuildToolBean = SbtBuildToolBean.build(dependencyBuilder);
                projectInfoBean.setSbtBuildToolBean(sbtBuildToolBean);
            } else {
                throw new RuntimeException(buildTool + " not yet implemented");
            }
        }
    }

    private static String getPropertyName(String propName) {
        StringBuilder projectNameSB = new StringBuilder();
        projectNameSB.append(Character.toUpperCase(propName.charAt(0)));
        for (int i = 1; i < propName.length(); i++) {
            if (Character.isUpperCase(propName.charAt(i))) {
                projectNameSB.append(" ");
            }
            projectNameSB.append(propName.charAt(i));
        }
        return projectNameSB.toString();
    }

    private static CodeTemplateBean getCodeTemplateBean(BaseTemplate template) {
        CodeTemplateBean codeTemplateBean = new CodeTemplateBean();
        codeTemplateBean.setCodeTemplate(template.codeTemplate());
        codeTemplateBean.setClassTemplate(template.classTemplate());
        codeTemplateBean.setImportTemplate(template.importTemplate());
        codeTemplateBean.setMethodsTemplate(template.methodsTemplate());
        codeTemplateBean.setSparkSessionBuildTemplate(template.sparkSessionBuildTemplate());
        codeTemplateBean.setSparkSessionCloseTemplate(template.sparkSessionCloseTemplate());
        return codeTemplateBean;
    }

    private static TemplateType getTemplateType(String templateTypeName) {
        TemplateType[] templateTypes = TemplateType.values();
        TemplateType templateType = null;
        for (TemplateType tType : templateTypes) {
            if (tType.name().equals(templateTypeName.toUpperCase())) {
                templateType = tType;
                break;
            }
        }

        if (templateType == null) {
            throw new RuntimeException("TemplateType not found");
        }
        return templateType;
    }

    private static String getPackageName(String projectName, ProjectDetailBean projectDetailBean) {
        String projectPackage = projectName
                .replace(projectDetailBean.getDelimiter() + projectDetailBean.getProjectExtension(), "")
                .replace(projectDetailBean.getDelimiter(), ".");

        String basePackage = projectConfig.getBasePackageName();
        if (basePackage.endsWith(".")) {
            return basePackage + projectPackage;
        } else {
            return basePackage + "." + projectPackage;
        }
    }

    private static String getProjectName(ProjectDetailBean projectDetailBean) {
        String appNameStr = projectDetailBean.getSourceProjectName();
        StringBuilder projectNameSB = new StringBuilder();
        for (int i = 0; i < appNameStr.length(); i++) {
            if (Character.isUpperCase(appNameStr.charAt(i))) {
                if (i != 0) {
                    projectNameSB.append(projectDetailBean.getDelimiter());
                }
                projectNameSB.append(Character.toLowerCase(appNameStr.charAt(i)));
            } else {
                projectNameSB.append(appNameStr.charAt(i));
            }
        }
        return projectNameSB.toString();
    }

    private static String getScalaBinaryVersion(String scalaBinaryVersion, String scalaVersion) {
        String tempScalaBinaryVersion = scalaVersion.substring(0, scalaVersion.lastIndexOf("."));
        if (scalaBinaryVersion == null || !tempScalaBinaryVersion.equals(scalaBinaryVersion)) {
            scalaBinaryVersion = tempScalaBinaryVersion;
        }
        return scalaBinaryVersion;
    }

    private static String getUpdatedValue(String value, String defaultValue) {
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
}
