package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.builder.DependencyBuilder;

import static com.ranga.spark.project.template.util.AppConstants.VERSION_DELIMITER;

public class SbtBuildToolBean extends BuildToolBean {

    private final String buildSbtName = "build.sbt";
    private final String buildPropertyName = "build.properties";
    private final String pluginsSbtName = "build.sbt";
    private String sbtVersion;

    public SbtBuildToolBean(String dependencies, String propertyVersions) {
        super(dependencies, propertyVersions);
    }

    public static SbtBuildToolBean build(DependencyBuilder dependencyBuilder) {
        StringBuilder propertyVersions = new StringBuilder();
        StringBuilder dependencies = new StringBuilder();

        for (String property : dependencyBuilder.getPropertyVersions()) {
            String[] split = property.split(VERSION_DELIMITER);
            String propertyName = split[0];
            String propertyValue = split[2];
            String propertyKey = "lazy val " + propertyName + " = " + propertyValue;
            propertyVersions.append(propertyKey).append("\n");
        }

        for (DependencyBean dependencyBean : dependencyBuilder.getDependencyBeanList()) {
            String groupId = dependencyBean.getGroupId();
            String artifactId = dependencyBean.getArtifactId();
            String version = dependencyBean.getVersion();
            String scope = dependencyBean.getScope();
            String scopeVal = "";
            if (scope != null && !scope.isEmpty()) {
                scopeVal = "% \"" + scope + "\"";
            }
            if (groupId.equals("org.apache.spark")) {
                dependencies.append("\"").append(groupId).append("\" %% \"").append(artifactId).append("\" % ").append(version)
                        .append(scopeVal).append(",");
            } else {
                dependencies.append("\"").append(groupId).append("\" % \"").append(artifactId).append("\" % ").append(version).
                        append(scopeVal).append(",");
            }
        }
        return new SbtBuildToolBean(dependencies.toString(), propertyVersions.toString());
    }

    public String getSbtVersion() {
        return sbtVersion;
    }

    public void setSbtVersion(String sbtVersion) {
        this.sbtVersion = sbtVersion;
    }

    public String getBuildSbtName() {
        return buildSbtName;
    }

    public String getBuildPropertyName() {
        return buildPropertyName;
    }

    public String getPluginsSbtName() {
        return pluginsSbtName;
    }

    @Override
    public String toString() {
        return "SbtBuildToolBean{" +
                "buildSbtName='" + buildSbtName + '\'' +
                ", buildPropertyName='" + buildPropertyName + '\'' +
                ", pluginsSbtName='" + pluginsSbtName + '\'' +
                ", sbtVersion='" + sbtVersion + '\'' +
                '}';
    }
}
