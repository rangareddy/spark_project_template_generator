package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.ProjectInfoBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static com.ranga.spark.project.template.util.AppConstants.*;

public class GenerateTemplateUtil {

    private static final Configuration cfg;

    static {
        cfg = new Configuration(new Version(TEMPLATE_VERSION));
        cfg.setClassForTemplateLoading(GenerateTemplateUtil.class, FTL_BASE_PACKAGE_PATH);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static void generateTemplate(String filePath, ProjectInfoBean projectInfoBean, String ftlFile) {
        generateTemplate(filePath, projectInfoBean, ftlFile, false);
    }

    public static void generateTemplate(String filePath, Object templateData, String ftlFile, boolean isCreateDir) {
        File templateFile = new File(filePath);
        if (isCreateDir) {
            templateFile.getParentFile().mkdirs();
        }
        GenerateTemplateUtil.generateTemplate(templateData, ftlFile, templateFile);
    }

    public static void generateTemplate(Object templateData, String ftlFile, File outputFile) {
        try {
            Template template = cfg.getTemplate(ftlFile);
            Map<String, Object> input = new HashMap<>();
            input.put(PROJECT_BUILDER, templateData);
            try (Writer fileWriter = new FileWriter(outputFile)) {
                template.process(input, fileWriter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}