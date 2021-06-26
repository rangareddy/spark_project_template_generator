package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.java.DefaultJavaTemplate;
import com.ranga.spark.project.template.api.java.HWCJavaTemplate;
import com.ranga.spark.project.template.api.scala.DefaultTemplate;
import com.ranga.spark.project.template.api.scala.HBaseTemplate;
import com.ranga.spark.project.template.api.scala.HWCTemplate;
import com.ranga.spark.project.template.api.scala.HiveTemplate;
import com.ranga.spark.project.template.bean.*;
import com.ranga.spark.project.template.util.AppConstants;
import com.ranga.spark.project.template.util.SparkSubmitBuildUtil;
import com.ranga.spark.project.template.util.TemplateType;
import com.ranga.spark.project.template.util.TemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import static com.ranga.spark.project.template.util.AppConstants.README_FILE;

public class ProjectBuilders implements Serializable {

    private static ProjectConfig projectConfig;
    private static Map<String, String> appRuntimeValuesMap;

    public static List<ProjectInfoBean> buildProjects(ProjectConfig config) {
        projectConfig = config;
        List<ProjectDetailBean> projectDetails = projectConfig.getProjectDetails();
        if (projectDetails == null || projectDetails.isEmpty()) {
            throw new RuntimeException("Project details are not specified in configuration file.");
        }

        String secureCluster = projectConfig.getSecureCluster();
        boolean isSecureCluster = false;
        if(StringUtils.isNotEmpty(secureCluster)) {
            isSecureCluster = new Boolean(secureCluster);
        }
        List<ProjectInfoBean> projectInfoBeanList = new ArrayList<>(projectDetails.size());
        String scalaVersion = projectConfig.getScalaVersion();
        String scalaBinaryVersion = getScalaBinaryVersion(projectConfig.getScalaBinaryVersion(), scalaVersion);
        String baseProjectDir = projectConfig.getBaseProjectDir();
        String baseDeployJarPath = projectConfig.getBaseDeployJarPath();
        String javaVersion = projectConfig.getJavaVersion();
        appRuntimeValuesMap = getAppRuntimeValueMap(projectConfig);

        for (ProjectDetailBean projectDetail : projectDetails) {
            TemplateType templateType = TemplateUtil.getTemplateType(projectDetail.getTemplateName());
            String sourceProjectName = projectDetail.getSourceProjectName();
            String projectName = getProjectName(projectDetail);
            String projectVersion = getUpdatedValue(projectDetail.getProjectVersion(), projectConfig.getJarVersion());
            String projectDir = baseProjectDir + File.separator + projectName;
            String packageName = getPackageName(projectName, projectDetail);
            String packageDir = packageName.replace(".", "/");
            String delimiter = projectDetail.getDelimiter();
            String className = sourceProjectName + "App";
            String javaClassName = sourceProjectName + "JavaApp";
            String jarVersion = projectConfig.getJarVersion();
            String jarName = projectName + "-" + jarVersion + ".jar";
            String fullClassName = packageName + "." + className;
            String jarDeployPath = baseDeployJarPath + projectName;
            String jarPath = jarDeployPath + File.separator + jarName;
            String runScriptName = "run_" + projectName.replace(delimiter, "_") + "_app.sh";
            String runScriptPath = projectDir + File.separator + runScriptName;
            String readMePath = projectDir + File.separator + README_FILE;
            String deployScriptPath = jarDeployPath + File.separator + runScriptName;
            String repoName = getRepositoryNames();

            ProjectInfoBean projectInfoBean = new ProjectInfoBean();
            projectInfoBean.setProjectName(projectName);
            projectInfoBean.setSourceProjectName(sourceProjectName);
            projectInfoBean.setProjectVersion(projectVersion);
            projectInfoBean.setScalaVersion(scalaVersion);
            projectInfoBean.setJavaVersion(javaVersion);
            projectInfoBean.setScalaBinaryVersion(scalaBinaryVersion);
            projectInfoBean.setProjectDirectory(projectDir);
            projectInfoBean.setJarVersion(jarVersion);
            projectInfoBean.setPackageName(packageName);
            projectInfoBean.setDelimiter(delimiter);
            projectInfoBean.setPackageDir(packageDir);
            projectInfoBean.setBaseDeployJarPath(baseDeployJarPath);
            projectInfoBean.setTemplateType(templateType);
            projectInfoBean.setClassName(className);
            projectInfoBean.setJavaClassName(javaClassName);
            projectInfoBean.setFullClassName(fullClassName);
            projectInfoBean.setJarName(jarName);
            projectInfoBean.setJarDeployPath(jarDeployPath);
            projectInfoBean.setJarPath(jarPath);
            projectInfoBean.setRunScriptName(runScriptName);
            projectInfoBean.setRunScriptPath(runScriptPath);
            projectInfoBean.setReadMePath(readMePath);
            projectInfoBean.setDeployScriptPath(deployScriptPath);
            projectInfoBean.setRepoName(repoName);
            projectInfoBean.setSecureCluster(isSecureCluster);
            buildTemplates(projectInfoBean);
            projectInfoBeanList.add(projectInfoBean);
        }
        return projectInfoBeanList;
    }

    private static String getRepositoryNames() {
        boolean isClouderaRepo = checkClouderaRepo(projectConfig.getSparkVersion());
        List<RepositoryBean> repositories = new ArrayList<>(3);
        repositories.add(new RepositoryBean("central", "Maven Central", "https://repo1.maven.org/maven2"));
        if (isClouderaRepo) {
            repositories.add(new RepositoryBean("cldr-repo", "Cloudera Public Repo", "https://repository.cloudera.com/artifactory/cloudera-repos/"));
            repositories.add(new RepositoryBean("hdp-repo", "Hortonworks Public Repo", "https://repo.hortonworks.com/content/repositories/releases/"));
        }

        StringBuilder repoSB = new StringBuilder();
        repoSB.append("\n");
        for(RepositoryBean repositoryBean : repositories) {
            repoSB.append("\t\t<repository>\n");
            repoSB.append("\t\t\t<id>").append(repositoryBean.getId()).append("</id>\n");
            repoSB.append("\t\t\t<name>").append(repositoryBean.getName()).append("</name>\n");
            repoSB.append("\t\t\t<url>").append(repositoryBean.getUrl()).append("</url>\n");
            repoSB.append("\t\t</repository>\n");
            repoSB.append("\n");
        }
        return repoSB.toString();
    }

    private static boolean checkClouderaRepo(String sparkVersion) {
        int count = 0;
        int index;
        while ((index = sparkVersion.indexOf(".")) != -1) {
            count++;
            sparkVersion = sparkVersion.substring(index + 1);
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
        SparkSubmitBean sparkSubmitBean = new SparkSubmitBean();

        List<String> argumentList = new ArrayList<>();
        if(projectInfoBean.isSecureCluster()) {
            List<String> secureArgumentList = new ArrayList<>();
            secureArgumentList.add("<PRINCIPAL>");
            secureArgumentList.add("<KEYTAB>");
            sparkSubmitBean.setSecureArgumentList(secureArgumentList);
        }

        String setupInstructions = "";
        switch (templateType) {
            case HBASE:
                template = new HBaseTemplate(className);
                othersTemplatesDependency = projectConfig.getHbaseTemplate();
                setupInstructions = template.setupInstructions();
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

        sparkSubmitBean.setArgumentList(argumentList);
        CodeTemplateBean codeTemplateBean = TemplateUtil.getCodeTemplateBean(template);
        projectInfoBean.setScalaCodeTemplate(codeTemplateBean);
        projectInfoBean.setSetUpInstructions(setupInstructions);

        if (javaTemplate != null) {
            projectInfoBean.setJavaTemplate(true);
            codeTemplateBean = TemplateUtil.getCodeTemplateBean(javaTemplate);
            projectInfoBean.setJavaCodeTemplate(codeTemplateBean);
        }

        Set<DependencyBean> dependencyBeanSet = new LinkedHashSet<>(defaultTemplateDependency);
        if (CollectionUtils.isNotEmpty(othersTemplatesDependency)) {
            dependencyBeanSet.addAll(othersTemplatesDependency);
        }
        buildDependencies(dependencyBeanSet, projectInfoBean);
        SparkSubmitBuildUtil.buildSparkSubmit(sparkSubmitBean, projectInfoBean);
    }

    private static Map<String, String> getAppRuntimeValueMap(Object obj) {
        Map<String, String> runtimeValues = new LinkedHashMap<>();
        try {
            Class myClass = obj.getClass();
            Field[] fields = myClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);
                if (value instanceof String) {
                    runtimeValues.put(key, value.toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return runtimeValues;
    }

    private static void buildDependencies(Set<DependencyBean> dependencyBeanSet,
                                          ProjectInfoBean projectInfoBean) {

        DependencyBuilder dependencyBuilder = DependencyBuilder.build(dependencyBeanSet, appRuntimeValuesMap);

        Set<String> propertyVersions = dependencyBuilder.getPropertyVersions();
        List<String> PrerequisitesList = new ArrayList<>(propertyVersions.size());
        for (String propVersion : propertyVersions) {
            String[] split = propVersion.split(AppConstants.VERSION_DELIMITER);
            String propName = split[0];
            String propValue = split[2];
            if (propName.toLowerCase().endsWith("version") && !propName.contains("Binary")) {
                String propertyName = getPropertyName(propName);
                PrerequisitesList.add(propertyName + " : " + propValue);
            }
        }
        projectInfoBean.setPrerequisitesList(PrerequisitesList);

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
        if (!StringUtils.equals(tempScalaBinaryVersion, scalaBinaryVersion)) {
            scalaBinaryVersion = tempScalaBinaryVersion;
        }
        return scalaBinaryVersion;
    }

    private static String getUpdatedValue(String value, String defaultValue) {
        return StringUtils.defaultString(value, defaultValue);
    }
}
