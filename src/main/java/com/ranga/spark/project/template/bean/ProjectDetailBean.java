package com.ranga.spark.project.template.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailBean implements Serializable {

    private String projectName;
    private String templateName = "default";
    private String description = "";
    private String projectVersion;
    private String projectDir;
    private String packageName;
    private String delimiter = "-";
    private String projectExtension = "integration";
}