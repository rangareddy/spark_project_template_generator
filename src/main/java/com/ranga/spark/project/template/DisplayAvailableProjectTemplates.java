package com.ranga.spark.project.template;

import com.ranga.spark.project.template.util.TemplateType;

public class DisplayAvailableProjectTemplates {
    public static void main(String[] args) {
        System.out.println("Currently available templates");
        TemplateType[] templateTypes = TemplateType.values();
        for(TemplateType templateType: templateTypes) {
            System.out.println(templateType.name());
        }
    }
}