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

public class CodeTemplateBean implements Serializable {

    private String importTemplate;
    private String classTemplate;
    private String sparkSessionBuildTemplate;
    private String sparkSessionCloseTemplate;
    private String codeTemplate;
    private String methodsTemplate;
}
