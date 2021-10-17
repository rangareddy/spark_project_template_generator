package com.ranga.spark.project.template.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildToolBean implements Serializable {

    private String dependencies;
    private String propertyVersions;
}