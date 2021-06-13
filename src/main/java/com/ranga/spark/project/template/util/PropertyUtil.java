package com.ranga.spark.project.template.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    public static Properties loadProperties(String args[]) throws Exception{
        Properties pr = new Properties();
        InputStream inputStream = null;
        String propertyFile = null;
        if(args.length == 0) {
            propertyFile = "application.properties";
            inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFile);
        } else {
            propertyFile = args[0];
            inputStream = new FileInputStream(propertyFile);
        }
        if (inputStream == null) {
            throw new FileNotFoundException("Property file is not found in the classpath");
        }
        pr.load(inputStream);
        return pr;
    }
}
