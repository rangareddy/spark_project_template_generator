package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class BuildToolBean implements Serializable {

    private String dependencies;
    private String propertyVersions;

    public BuildToolBean(String dependencies, String propertyVersions) {
        this.dependencies = dependencies;
        this.propertyVersions = propertyVersions;
    }

    public String getPropertyVersions() {
        return propertyVersions;
    }

    public void setPropertyVersions(String propertyVersions) {
        this.propertyVersions = propertyVersions;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return "BuildToolBean{" +
                "dependencies='" + dependencies + '\'' +
                ", propertyVersions='" + propertyVersions + '\'' +
                '}';
    }
}