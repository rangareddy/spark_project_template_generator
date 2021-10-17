package com.ranga.spark.project.template.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DependencyBean implements Serializable {

    private String groupId;
    private String artifactId;
    private String version;
    private String scope;
}