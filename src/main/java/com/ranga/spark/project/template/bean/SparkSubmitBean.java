package com.ranga.spark.project.template.bean;

import java.io.Serializable;
import java.util.*;

public class SparkSubmitBean implements Serializable {

    private String name;
    private String master = "yarn";
    private String deployMode = "client";
    private String driverMemory = "1g";
    private String executorMemory = "1g";
    private String numExecutors = "2";
    private String executorCores = "3";
    private String driverCores = "1";
    private String className;
    private String jarPath;
    private List<String> fileList = Collections.emptyList();
    private List<String> argumentList = Collections.emptyList();
    private Map<String, String> otherConfMap = new LinkedHashMap<>();
    private List<String> secureArgumentList = Arrays.asList("--principal", "--keytab");
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

    public void setNumExecutors(String numExecutors) {
        this.numExecutors = numExecutors;
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

    public void setExecutorCores(String executorCores) {
        this.executorCores = executorCores;
    }

    public String getDriverCores() {
        return driverCores;
    }

    public void setDriverCores(String driverCores) {
        this.driverCores = driverCores;
    }

    public List<String> getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(List<String> argumentList) {
        this.argumentList = argumentList;
    }

    public List<String> getSecureArgumentList() {
        return secureArgumentList;
    }

    public void setSecureArgumentList(List<String> secureArgumentList) {
        this.secureArgumentList = secureArgumentList;
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
                ", argumentList=" + argumentList +
                ", otherConfMap=" + otherConfMap +
                ", secureArgumentList=" + secureArgumentList +
                ", files='" + files + '\'' +
                ", driverClassPath='" + driverClassPath + '\'' +
                ", executorClassPath='" + executorClassPath + '\'' +
                ", argumentsCondition='" + argumentsCondition + '\'' +
                '}';
    }
}
