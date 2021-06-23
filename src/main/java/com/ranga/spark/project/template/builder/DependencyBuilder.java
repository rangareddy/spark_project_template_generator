package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.SparkProjectTemplateGenerator;
import com.ranga.spark.project.template.bean.DependencyBean;
import com.ranga.spark.project.template.bean.ProjectConfig;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class DependencyBuilder implements Serializable {

    private List<DependencyBean> dependencyBeanList;
    private Set<String> propertyVersions;

    public DependencyBuilder(List<DependencyBean> dependencyBeanList, Set<String> versions) {
        this.dependencyBeanList = dependencyBeanList;
        this.propertyVersions = versions;
    }

    public static DependencyBuilder build(Set<DependencyBean> dependencyBeanSet) {
        Map<String, String> appRuntimeValueMap = getAppRuntimeValueMap();
        List<DependencyBean> dependencyBeanList = new ArrayList<>(dependencyBeanSet.size());
        Set<String> versions = new LinkedHashSet<>();
        for (DependencyBean dependencyBean : dependencyBeanSet) {
            String groupId = getUpdateDependency(dependencyBean.getGroupId(), versions, appRuntimeValueMap);
            String artifactId = getUpdateDependency(dependencyBean.getArtifactId(), versions, appRuntimeValueMap);
            String version = getUpdateDependency(dependencyBean.getVersion(), versions, appRuntimeValueMap);
            String scope = getUpdateDependency(dependencyBean.getScope(), versions, appRuntimeValueMap);
            DependencyBean updatedDependencyBean = new DependencyBean(groupId, artifactId, version, scope);
            dependencyBeanList.add(updatedDependencyBean);
        }
        String scalaVersionKey = "scalaVersion";
        versions.add("javaVersion##java.version##1.8");
        versions.add(scalaVersionKey + "##" + "scala.version" + "##" + appRuntimeValueMap.get(scalaVersionKey));
        return new DependencyBuilder(dependencyBeanList, versions);
    }

    private static String getVersionKey(String key) {
        StringBuilder versionSB = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            if (Character.isUpperCase(key.charAt(i))) {
                if (i != 0) {
                    versionSB.append(".");
                }
                versionSB.append(Character.toLowerCase(key.charAt(i)));
            } else {
                versionSB.append(key.charAt(i));
            }
        }
        return versionSB.toString();
    }

    private static String getUpdateDependency(String propertyName, Set<String> versions, Map<String, String> runtimeValueMap) {
        String updatedDependency = propertyName;
        if (propertyName.contains("${")) {
            Set<String> keySet = runtimeValueMap.keySet();
            for (String key : keySet) {
                if (propertyName.contains(key)) {
                    String versionValue = runtimeValueMap.get(key);
                    String versionKey = getVersionKey(key);
                    versions.add(key + "##" + versionKey + "##" + versionValue);
                    updatedDependency = propertyName.replace(key, versionKey);
                    break;
                }
            }
        }
        return updatedDependency;
    }

    private static Map<String, String> getAppRuntimeValueMap() {
        Map<String, String> runtimeValues = new LinkedHashMap<>();
        ClassLoader classLoader = SparkProjectTemplateGenerator.class.getClassLoader();
        try {
            Class aClass = classLoader.loadClass(ProjectConfig.class.getName());
            Field[] fields = aClass.getDeclaredFields();
            Object instance = aClass.newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(instance);
                if (value instanceof String) {
                    runtimeValues.put(key, value.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return runtimeValues;
    }

    public List<DependencyBean> getDependencyBeanList() {
        return dependencyBeanList;
    }

    public void setDependencyBeanList(List<DependencyBean> dependencyBeanList) {
        this.dependencyBeanList = dependencyBeanList;
    }

    public Set<String> getPropertyVersions() {
        return propertyVersions;
    }

    public void setPropertyVersions(Set<String> propertyVersions) {
        this.propertyVersions = propertyVersions;
    }
}