package com.ranga.spark.project.template.bean;

import java.io.Serializable;

public class RepositoryBean implements Serializable {
    private final String id;
    private final String name;
    private final String url;

    public RepositoryBean(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "RepositoryBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
