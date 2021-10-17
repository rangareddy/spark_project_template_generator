package com.ranga.spark.project.template.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unused")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SparkSubmitBean implements Serializable {

    private String name;
    private final String master = "yarn";
    private final String deployMode = "client";
    private final String driverMemory = "1g";
    private final String executorMemory = "1g";
    private final String numExecutors = "2";
    private final String executorCores = "3";
    private final String driverCores = "1";
    private String className;
    private String jarPath;
    private List<String> fileList = Collections.emptyList();
    private List<String> usageArgumentList = Collections.emptyList();
    private List<String> appArgumentList = Collections.emptyList();
    private final List<String> secureArgumentList = Arrays.asList("principal", "keytab");
    private Map<String, String> otherConfMap = new LinkedHashMap<>();
    private String files;
    private String driverClassPath;
    private String executorClassPath;
    private String argumentsCondition;
}
