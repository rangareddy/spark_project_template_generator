package com.ranga.spark.project.template.bean;

import java.io.Serializable;
import java.util.Objects;

public class DependencyBean implements Serializable {

    private String groupId;
    private String artifactId;
    private String version;
    private String scope;

    public DependencyBean() {

    }

    public DependencyBean(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, "");
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

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyBean that = (DependencyBean) o;
        return groupId.equals(that.groupId) && artifactId.equals(that.artifactId) && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version);
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
