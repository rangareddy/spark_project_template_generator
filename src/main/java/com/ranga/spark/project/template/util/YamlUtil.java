package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.ProjectConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;

public class YamlUtil {
    public static ProjectConfig loadYamlFile(String[] args) {
        InputStream inputStream  = null;
        String resourceName = "config.yaml";
        try {
            if (args.length > 0 && args[0].endsWith("yaml")) {
                resourceName = args[0];
                inputStream = new FileInputStream(resourceName);
            } else {
                inputStream = YamlUtil.class.getClassLoader().getResourceAsStream(resourceName);
            }
            Yaml yaml = new Yaml(new Constructor(ProjectConfig.class));
            return yaml.load(inputStream);
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred while reading the "+resourceName, ex);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}