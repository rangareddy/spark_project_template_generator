package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.bean.DependencyBean;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

import static com.ranga.spark.project.template.util.AppConstants.VERSION_DELIMITER;

public class DependencyBuilder implements Serializable {

    private List<DependencyBean> dependencyBeanList;
    private Set<String> propertyVersions;

    public DependencyBuilder(List<DependencyBean> dependencyBeanList, Set<String> versions) {
        this.dependencyBeanList = dependencyBeanList;
        this.propertyVersions = versions;
    }

    public static DependencyBuilder build(Set<LinkedHashMap> dependencyBeanSet, Map<String, String> appRuntimeValueMap) {
        List<DependencyBean> dependencyBeanList = new ArrayList<>(dependencyBeanSet.size());
        Set<String> versions = new LinkedHashSet<>();
        for (LinkedHashMap<String,String> map : dependencyBeanSet) {
            String groupId = getUpdateDependency(map.get("groupId"), versions, appRuntimeValueMap);
            String artifactId = getUpdateDependency(map.get("artifactId"), versions, appRuntimeValueMap);
            String version = getUpdateDependency(map.get("version"), versions, appRuntimeValueMap);
            String scope = getUpdateDependency(map.get("scope"), versions, appRuntimeValueMap);
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
        if(StringUtils.isEmpty(propertyName) || !propertyName.contains("${")) {
            return propertyName;
        }
        String updatedDependency = propertyName;
        Set<String> keySet = runtimeValueMap.keySet();
        for (String key : keySet) {
            if (propertyName.contains(key)) {
                String versionValue = runtimeValueMap.get(key);
                String versionKey = getVersionKey(key);
                versions.add(key + VERSION_DELIMITER + versionKey + VERSION_DELIMITER + versionValue);
                updatedDependency = propertyName.replace(key, versionKey);
                break;
            }
        }
        return updatedDependency;
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