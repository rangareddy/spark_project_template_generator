package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.*;
import com.ranga.spark.project.template.builder.DependencyBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

import static com.ranga.spark.project.template.util.AppConstants.*;

public class AppUtil implements Serializable {

    public static List<ProjectDetailBean> getProjectDetails(ProjectConfig projectConfig) {
        List<ProjectDetailBean> projectDetails = projectConfig.getProjectDetails();
        if (CollectionUtils.isEmpty(projectDetails)) {
            throw new RuntimeException("Project details are not specified in configuration file.");
        }
        return projectDetails;
    }

    public static String getProjectName(String name, String extension) {
        String titleName = getTitleCase(name);
        String titleExtension = getTitleCase(extension);
        return StringUtils.isNotEmpty(titleExtension) ? titleName + " " + titleExtension : titleName;
    }

    public static String getProjectName(String sourceProjectName) {
        String[] camelCaseWords = sourceProjectName.split("(?=[A-Z])");
        return String.join("-", camelCaseWords).toLowerCase();
    }

    public static String getScalaBinaryVersion(String scalaBinaryVersion, String scalaVersion) {
        String tempScalaBinaryVersion = scalaVersion.substring(0, scalaVersion.lastIndexOf(DOT_DELIMITER));
        if (!StringUtils.equals(tempScalaBinaryVersion, scalaBinaryVersion)) {
            scalaBinaryVersion = tempScalaBinaryVersion;
        }
        return scalaBinaryVersion;
    }

    public static String getTitleCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        StringBuilder converted = new StringBuilder();
        boolean convertNext = true;
        int currentIndex = -1;
        for (char ch : str.toCharArray()) {
            currentIndex++;
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                if(Character.isUpperCase(ch)) {
                    char prevChar = str.charAt(currentIndex -1);
                    if(Character.isLowerCase(prevChar) && Character.isUpperCase(ch)) {
                        converted.append(" ");
                    }
                    if(Character.isUpperCase(prevChar)) {
                        ch = Character.toLowerCase(ch);
                    }
                } else {
                    ch = Character.toLowerCase(ch);
                }
            }
            converted.append(ch);
        }
        return converted.toString();
    }

    public static String getRepositoryNames(String sparkVersion) {
        boolean isClouderaRepo = AppUtil.checkClouderaRepo(sparkVersion);
        List<RepositoryBean> repositories = new ArrayList<>(isClouderaRepo ? 3 : 1);
        repositories.add(new RepositoryBean("central", "Maven Central", "https://repo1.maven.org/maven2"));
        if (isClouderaRepo) {
            repositories.add(new RepositoryBean("cldr-repo", "Cloudera Public Repo", "https://repository.cloudera.com/artifactory/cloudera-repos/"));
            repositories.add(new RepositoryBean("hdp-repo", "Hortonworks Public Repo", "https://repo.hortonworks.com/content/repositories/releases/"));
        }

        StringBuilder repoSB = new StringBuilder();
        repoSB.append("\n");
        for (RepositoryBean repositoryBean : repositories) {
            repoSB.append("\t\t").append("<repository>\n");
            repoSB.append("\t\t\t").append("<id>").append(repositoryBean.getId()).append("</id>\n");
            repoSB.append("\t\t\t").append("<name>").append(repositoryBean.getName()).append("</name>\n");
            repoSB.append("\t\t\t").append("<url>").append(repositoryBean.getUrl()).append("</url>\n");
            repoSB.append("\t\t").append("</repository>\n");
            repoSB.append("\n");
        }
        return repoSB.toString().trim();
    }

    public static boolean checkClouderaRepo(String sparkVersion) {
        return sparkVersion.split("\\.").length > 2;
    }

    public static Map<String, String> getAppRuntimeValueMap(ProjectConfig projectConfig) {
        Map<String, String> runtimeValues = new LinkedHashMap<>();
        try {
            Object obj = projectConfig;
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value instanceof String) {
                    String key = field.getName();
                    runtimeValues.put(key, value.toString());
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred while getting runtime values from object", ex);
        }
        List<ComponentDetailBean> componentDetails = projectConfig.getComponentVersions();
        if(CollectionUtils.isNotEmpty(componentDetails)) {
            for(ComponentDetailBean componentDetailBean: componentDetails) {
                String componentName = componentDetailBean.getComponent();
                String version = componentDetailBean.getVersion();
                String scope = componentDetailBean.getScope();
                runtimeValues.put(componentName+"Version", version);
                if(StringUtils.isNotEmpty(scope) || StringUtils.isNotEmpty(projectConfig.getScope())) {
                    runtimeValues.put(componentName + "Scope", StringUtils.isNotEmpty(scope) ? scope : projectConfig.getScope());
                }
            }
        } else {
            throw new RuntimeException("componentVersions is not present in configuration file.");
        }
        if(!runtimeValues.containsKey("sparkVersion")) {
            throw new RuntimeException("spark component details are mandatory. Please specify using componentVersions in configuration file.");
        }
        return runtimeValues;
    }

    public static String getUpdatedValue(String value, String defaultValue) {
        return StringUtils.defaultString(value, defaultValue);
    }

    public static String getPropertyName(String propName) {
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

    public static String getPackageName(String projectName, String basePackage, ProjectDetailBean projectDetailBean) {
        String projectPackage = projectName
                .replace(projectDetailBean.getDelimiter() + projectDetailBean.getProjectExtension(), "")
                .replace(projectDetailBean.getDelimiter(), DOT_DELIMITER);
        return basePackage.endsWith(DOT_DELIMITER) ? (basePackage + projectPackage) : (basePackage + DOT_DELIMITER + projectPackage);
    }

    public static void buildDependencies(ProjectConfig projectConfig, Set<DependencyBean> dependencyBeanSet,
                                         ProjectInfoBean projectInfoBean, Map<String, String> projectConfigMap) {

        DependencyBuilder dependencyBuilder = DependencyBuilder.build(dependencyBeanSet, projectConfigMap);
        Set<String> propertyVersions = dependencyBuilder.getPropertyVersions();
        List<String> PrerequisitesList = new ArrayList<>(propertyVersions.size());
        for (String propVersion : propertyVersions) {
            String[] split = propVersion.split(AppConstants.VERSION_DELIMITER);
            String propName = split[0];
            String propValue = split[2];
            if (propName.toLowerCase().endsWith(VERSION) && !propName.contains(BINARY)) {
                String propertyName = AppUtil.getPropertyName(propName);
                PrerequisitesList.add(propertyName + " : " + propValue);
            }
        }
        projectInfoBean.setPrerequisitesList(PrerequisitesList);

        for (String buildTool : projectConfig.getBuildTools().split(COMMA_DELIMITER)) {
            if (MAVEN_BUILD_TOOL.equals(buildTool)) {
                MavenBuildToolBean mavenBuildToolBean = MavenBuildToolBean.build(dependencyBuilder);
                projectInfoBean.setMavenBuildToolBean(mavenBuildToolBean);
            } else if (AppConstants.SBT_BUILD_TOOL.equals(buildTool)) {
                SbtBuildToolBean sbtBuildToolBean = SbtBuildToolBean.build(dependencyBuilder);
                projectInfoBean.setSbtBuildToolBean(sbtBuildToolBean);
            } else {
                throw new RuntimeException(buildTool + " not yet implemented");
            }
        }
    }

    public static String getSourceProjectName(String name) {
        return name.replaceAll(" ","");
    }
}