package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.ProjectConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class YamlUtil {
    public static ProjectConfig loadYamlFile() {
        Yaml yaml = new Yaml(new Constructor(ProjectConfig.class));
        InputStream inputStream = YamlUtil.class.getClassLoader().getResourceAsStream("config.yaml");
        return yaml.load(inputStream);
    }
}