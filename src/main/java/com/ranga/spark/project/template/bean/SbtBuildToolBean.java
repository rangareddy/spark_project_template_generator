package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.builder.DependencyBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> propertyMap = new LinkedHashMap<>();
        for (String property : dependencyBuilder.getPropertyVersions()) {
            String[] split = property.split(VERSION_DELIMITER);
            String propertyName = split[0];
            String propertyName1 = split[1];
            String propertyValue = split[2];

            if(propertyName.contains("-")) {
                propertyName = propertyName.replace("-","_");
            }

            propertyMap.put(propertyName1, propertyName);

            String propertyKey = null;
            if ("scalaVersion".equals(propertyName)) {
                propertyKey = "" + propertyName + " := \"" + propertyValue + "\"";
            } else {
                propertyKey = "val " + propertyName + " = \"" + propertyValue + "\"";
            }
            propertyVersions.append(propertyKey).append("\n");
        }


        List<DependencyBean> dependencyBeanList = dependencyBuilder.getDependencyBeanList();
        int size = dependencyBeanList.size();
        for (int i = 0; i < size; i++) {
            DependencyBean dependencyBean = dependencyBeanList.get(i);
            String groupId = dependencyBean.getGroupId();
            String artifactId = dependencyBean.getArtifactId();
            int index = artifactId.indexOf("${");
            if(index != -1){
                String aId = propertyMap.get(artifactId.replace("${", "").replace("}", "").substring(index));
                artifactId = artifactId.substring(0, index+2)+aId+"}";
            }
            String version = getUpdatedProperty(dependencyBean.getVersion(), propertyMap);
            String scope = getUpdatedProperty(dependencyBean.getScope(), propertyMap);
            String scopeVal = "";
            if (scope != null && !scope.isEmpty()) {
                scopeVal = " % " + scope;
            }

            String dependencyDelimiter = (i == size - 1) ? "" : ",\n";
            dependencies.append("\t\"").append(groupId).
                    append("\" % s\"").append(artifactId).
                    append("\" % ").append(version).
                    append(scopeVal).append(dependencyDelimiter);
        }
        return new SbtBuildToolBean(dependencies.toString(), propertyVersions.toString());
    }

    private static String getUpdatedProperty(String scope, Map<String, String> propertyMap) {
        if(scope == null) {
            return null;
        }
        String value = scope.replace("${", "").replace("}", "");
        return propertyMap.get(value);
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
