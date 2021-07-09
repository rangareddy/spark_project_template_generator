package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class ComponentDetailBean implements Serializable {
    private String component;
    private String version;
    private String scope;

    public ComponentDetailBean() {
    }

    public ComponentDetailBean(String component, String version, String scope) {
        this.component = component;
        this.version = version;
        this.scope = scope;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
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
    public String toString() {
        return "ComponentDetailsBean{" +
                "component='" + component + '\'' +
                ", version='" + version + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
