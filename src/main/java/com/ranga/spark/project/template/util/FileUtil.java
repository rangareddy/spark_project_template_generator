package com.ranga.spark.project.template.util;

import java.io.File;

public class FileUtil {
    public static void createDir(File dirFile) {
        if (dirFile.exists()) {
            deleteProject(dirFile);
        }
        dirFile.mkdirs();
        System.out.println(dirFile +" created successfully");
    }

    public static void deleteProject(File dir) {
        File[] files = dir.listFiles();
        if(files != null) {
            for (final File file : files) {
                deleteProject(file);
            }
        }
        dir.delete();
    }
}
