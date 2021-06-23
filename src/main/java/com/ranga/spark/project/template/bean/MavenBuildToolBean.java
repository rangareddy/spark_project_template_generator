package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.builder.DependencyBuilder;
import static com.ranga.spark.project.template.util.AppConstants.VERSION_DELIMITER;

public class MavenBuildToolBean extends BuildToolBean {

    private static final String pomFile = "pom.xml";
    public MavenBuildToolBean(String dependencies, String propertyVersions) {
        super(dependencies, propertyVersions);
    }

    public static MavenBuildToolBean build(DependencyBuilder dependencyBuilder) {
        StringBuilder propertyVersions = new StringBuilder();
        StringBuilder dependencies = new StringBuilder();
        propertyVersions.append("\n");
        String tabDelim = "\t\t";
        for (String property : dependencyBuilder.getPropertyVersions()) {
            String[] split = property.split(VERSION_DELIMITER);
            String propertyName = split[1];
            String propertyValue = split[2];
            String propertyKey = "<" + propertyName + ">" + propertyValue + "</" + propertyName + ">";
            propertyVersions.append(tabDelim).append(propertyKey).append("\n");
        }

        for (DependencyBean dependencyBean : dependencyBuilder.getDependencyBeanList()) {
            String groupId = dependencyBean.getGroupId();
            String artifactId = dependencyBean.getArtifactId();
            String version = dependencyBean.getVersion();
            String scope = dependencyBean.getScope();

            StringBuilder xmlString = new StringBuilder("\n");
            xmlString.append(tabDelim).append("<dependency>\n");
            xmlString.append(tabDelim).append("\t<groupId>").append(groupId).append("</groupId>\n");
            xmlString.append(tabDelim).append("\t<artifactId>").append(artifactId).append("</artifactId>\n");
            xmlString.append(tabDelim).append("\t<version>").append(version).append("</version>\n");
            if (scope != null && !scope.isEmpty()) {
                xmlString.append(tabDelim).append("\t<scope>").append(scope).append("</scope>\n");
            }
            xmlString.append(tabDelim).append("</dependency>");

            dependencies.append(xmlString).append("\n");
        }
        return new MavenBuildToolBean(dependencies.toString(), propertyVersions.toString());
    }

    public String getPomFile() {
        return pomFile;
    }

    @Override
    public String toString() {
        return "MavenBuildToolBean{" +
                "pomFile='" + pomFile + "'," +
                "propertyVersions='" + super.getPropertyVersions() + "'," +
                "dependencies='" + super.getDependencies() + "'," +
                '}';
    }
}