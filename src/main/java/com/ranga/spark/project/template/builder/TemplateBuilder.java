package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.java.DefaultJavaTemplate;
import com.ranga.spark.project.template.api.java.HWCJavaTemplate;
import com.ranga.spark.project.template.api.scala.DefaultTemplate;
import com.ranga.spark.project.template.api.scala.HBaseTemplate;
import com.ranga.spark.project.template.api.scala.HWCTemplate;
import com.ranga.spark.project.template.api.scala.HiveTemplate;
import com.ranga.spark.project.template.util.BuildDependencyUtil;
import com.ranga.spark.project.template.util.TemplateType;

import java.util.Properties;

public class TemplateBuilder {

    public static void buildTemplate(String templateTypeName, ProjectBuilder projectBuilder) {
        TemplateType[]templateTypes = TemplateType.values();
        TemplateType templateType = null;
        for(TemplateType tType : templateTypes) {
            if(tType.name().equals(templateTypeName.toUpperCase())) {
                templateType = tType;
                break;
            }
        }

        if(templateType == null) {
            throw new RuntimeException("TemplateType not found");
        }

        projectBuilder.setTemplateType(templateType);

        BaseTemplate template;
        BaseTemplate javaTemplate = null;
        String className = projectBuilder.getClassName();
        String javaClassName = projectBuilder.getJavaClassName();
        switch (templateType) {
            case HBASE :
                template = new HBaseTemplate(className);
                break;
            case HIVE:
                template = new HiveTemplate(className);
                break;
            case HWC:
                template = new HWCTemplate(className);
                javaTemplate = new HWCJavaTemplate(javaClassName);
                break;
            default:
                template = new DefaultTemplate(className);
                javaTemplate = new DefaultJavaTemplate(javaClassName);
        }

        // Building Dependencies
        projectBuilder.setDependencyBeanList(BuildDependencyUtil.buildDependency(projectBuilder));

        Properties prop = projectBuilder.getProperties();
        prop.setProperty("sparkSessionBuildTemplate", template.sparkSessionBuildTemplate());
        prop.setProperty("sparkSessionCloseTemplate", template.sparkSessionCloseTemplate());
        prop.setProperty("codeTemplate", template.codeTemplate());
        prop.setProperty("importTemplate", template.importTemplate());
        prop.setProperty("classTemplate", template.classTemplate());
        prop.setProperty("methodsTemplate", template.methodsTemplate());

        if(javaTemplate != null) {
            projectBuilder.setJavaTemplate(true);
            prop.setProperty("sparkSessionBuildJavaTemplate", javaTemplate.sparkSessionBuildTemplate());
            prop.setProperty("sparkSessionCloseJavaTemplate", javaTemplate.sparkSessionCloseTemplate());
            prop.setProperty("codeJavaTemplate", javaTemplate.codeTemplate());
            prop.setProperty("importJavaTemplate", javaTemplate.importTemplate());
            prop.setProperty("classJavaTemplate", javaTemplate.classTemplate());
            prop.setProperty("methodsJavaTemplate", javaTemplate.methodsTemplate());
        }
    }
}