package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class DependencyBean implements Serializable {

    private String groupId;
    private String artifactId;
    private String version;
    private String scope;

    public DependencyBean(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, "provided");
    }

    public DependencyBean(String groupId, String artifactId, String version, String scope) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "DependencyBean{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
