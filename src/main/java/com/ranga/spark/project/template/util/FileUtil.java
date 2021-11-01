package com.ranga.spark.project.template.util;

import java.io.File;

public class FileUtil {
    public static void createDir(File dirFile) {
        if (dirFile.exists()) {
            deleteProject(dirFile);
        }
        dirFile.mkdirs();
    }

    public static boolean deleteProject(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                deleteProject(file);
            }
        }
        return dir.delete();
    }
}
