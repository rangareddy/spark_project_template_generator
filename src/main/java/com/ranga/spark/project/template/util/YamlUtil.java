package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.ProjectConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class YamlUtil {
    public static ProjectConfig loadYamlFile(String[] args) throws FileNotFoundException {
        InputStream inputStream;
        if (args.length > 0 && args[0].endsWith("yaml")) {
            inputStream = new FileInputStream(args[0]);
        } else {
            inputStream = YamlUtil.class.getClassLoader().getResourceAsStream("config.yaml");
        }
        Yaml yaml = new Yaml(new Constructor(ProjectConfig.class));
        return yaml.load(inputStream);
    }
}