package com.ranga.spark.project.template.bean;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unused")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public String getDriverMemory() {
        return driverMemory;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public String getNumExecutors() {
        return numExecutors;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDriverClassPath() {
        return driverClassPath;
    }

    public void setDriverClassPath(String driverClassPath) {
        this.driverClassPath = driverClassPath;
    }

    public String getExecutorClassPath() {
        return executorClassPath;
    }

    public void setExecutorClassPath(String executorClassPath) {
        this.executorClassPath = executorClassPath;
    }

    public String getArgumentsCondition() {
        return argumentsCondition;
    }

    public void setArgumentsCondition(String argumentsCondition) {
        this.argumentsCondition = argumentsCondition;
    }

    public String getExecutorCores() {
        return executorCores;
    }

    public String getDriverCores() {
        return driverCores;
    }

    public List<String> getUsageArgumentList() {
        return usageArgumentList;
    }

    public void setUsageArgumentList(List<String> usageArgumentList) {
        this.usageArgumentList = usageArgumentList;
    }

    public List<String> getAppArgumentList() {
        return appArgumentList;
    }

    public void setAppArgumentList(List<String> appArgumentList) {
        this.appArgumentList = appArgumentList;
    }

    public List<String> getSecureArgumentList() {
        return secureArgumentList;
    }

    public Map<String, String> getOtherConfMap() {
        return otherConfMap;
    }

    public void setOtherConfMap(Map<String, String> otherConfMap) {
        this.otherConfMap = otherConfMap;
    }

    @Override
    public String toString() {
        return "SparkSubmitBean{" +
                "name='" + name + '\'' +
                ", master='" + master + '\'' +
                ", deployMode='" + deployMode + '\'' +
                ", driverMemory='" + driverMemory + '\'' +
                ", executorMemory='" + executorMemory + '\'' +
                ", numExecutors='" + numExecutors + '\'' +
                ", executorCores='" + executorCores + '\'' +
                ", driverCores='" + driverCores + '\'' +
                ", className='" + className + '\'' +
                ", jarPath='" + jarPath + '\'' +
                ", fileList=" + fileList +
                ", usageArgumentList=" + usageArgumentList +
                ", appArgumentList=" + appArgumentList +
                ", otherConfMap=" + otherConfMap +
                ", secureArgumentList=" + secureArgumentList +
                ", files='" + files + '\'' +
                ", driverClassPath='" + driverClassPath + '\'' +
                ", executorClassPath='" + executorClassPath + '\'' +
                ", argumentsCondition='" + argumentsCondition + '\'' +
                '}';
    }
}
