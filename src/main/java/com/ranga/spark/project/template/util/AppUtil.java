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

    public static String getProjectName(ProjectDetailBean projectDetailBean) {
        String appNameStr = projectDetailBean.getSourceProjectName();
        String[] camelCaseWords = appNameStr.split("(?=[A-Z])");
        return String.join("-", camelCaseWords).toLowerCase();
    }

    public static String getScalaBinaryVersion(String scalaBinaryVersion, String scalaVersion) {
        String tempScalaBinaryVersion = scalaVersion.substring(0, scalaVersion.lastIndexOf(DOT_DELIMITER));
        if (!StringUtils.equals(tempScalaBinaryVersion, scalaBinaryVersion)) {
            scalaBinaryVersion = tempScalaBinaryVersion;
        }
        return scalaBinaryVersion;
    }

    public static String getRepositoryNames(ProjectConfig projectConfig) {
        boolean isClouderaRepo = AppUtil.checkClouderaRepo(projectConfig.getSparkVersion());
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
        return repoSB.toString();
    }

    public static boolean checkClouderaRepo(String sparkVersion) {
        return sparkVersion.split(DOT_DELIMITER).length > 2;
    }

    public static Map<String, String> getAppRuntimeValueMap(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, String> runtimeValues = new LinkedHashMap<>(fields.length);
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value instanceof String) {
                    String key = field.getName();
                    runtimeValues.put(key, value.toString());
                }
            }
            return runtimeValues;
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred while getting runtime values from object", ex);
        }
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

    public static void buildDependencies(String buildToolStr, Set<DependencyBean> dependencyBeanSet,
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

        for (String buildTool : buildToolStr.split(COMMA_DELIMITER)) {
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
}