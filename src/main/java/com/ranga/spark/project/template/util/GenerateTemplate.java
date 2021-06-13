package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.builder.ProjectBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class GenerateTemplate {

    private static final Configuration cfg;

    static {
        cfg = new Configuration(new Version("2.3.31"));
        cfg.setClassForTemplateLoading(GenerateTemplate.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static void generateTemplate(ProjectBuilder projectBuilder, String ftlFile, File outputFile) {
        try {
            Template template = cfg.getTemplate(ftlFile);
            Map<String, Object> input = new HashMap<>();
            input.put("projectBuilder", projectBuilder);
            Writer fileWriter = null;
            try {
                fileWriter = new FileWriter(outputFile);
                template.process(input, fileWriter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                if(fileWriter != null) {
                    fileWriter.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}