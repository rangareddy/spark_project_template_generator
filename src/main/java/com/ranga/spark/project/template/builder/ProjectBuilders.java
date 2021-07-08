package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.bean.ProjectConfig;
import com.ranga.spark.project.template.bean.ProjectDetailBean;
import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.util.AppUtil;
import com.ranga.spark.project.template.util.TemplateType;
import com.ranga.spark.project.template.util.TemplateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ranga.spark.project.template.util.AppConstants.DOT_DELIMITER;
import static com.ranga.spark.project.template.util.AppConstants.README_FILE;

public class ProjectBuilders implements Serializable {

    public static List<ProjectInfoBean> buildProjects(ProjectConfig projectConfig) {
        List<ProjectDetailBean> projectDetails = AppUtil.getProjectDetails(projectConfig);

        String secureCluster = projectConfig.getSecureCluster();
        String sslCluster = projectConfig.getSslCluster();
        boolean isSecureCluster = StringUtils.isNotEmpty(secureCluster) && Boolean.parseBoolean(secureCluster);
        boolean isSSLCluster = StringUtils.isNotEmpty(sslCluster) && Boolean.parseBoolean(sslCluster);
        List<ProjectInfoBean> projectInfoBeanList = new ArrayList<>(projectDetails.size());
        String scalaVersion = projectConfig.getScalaVersion();
        String scalaBinaryVersion = AppUtil.getScalaBinaryVersion(projectConfig.getScalaBinaryVersion(), scalaVersion);
        String baseProjectDir = projectConfig.getBaseProjectDir();
        String baseDeployJarPath = projectConfig.getBaseDeployJarPath();
        String javaVersion = projectConfig.getJavaVersion();
        Map<String, String> projectConfigMap = AppUtil.getAppRuntimeValueMap(projectConfig);
        Date today = Calendar.getInstance().getTime();
        String createdDate = new SimpleDateFormat("MM/dd/yyyy").format(today);
        for (ProjectDetailBean projectDetail : projectDetails) {
            TemplateType templateType = TemplateUtil.getTemplateType(projectDetail.getTemplateName());
            String name = AppUtil.getProjectName(projectDetail.getProjectName(), projectDetail.getProjectExtension());
            if(StringUtils.isEmpty(name)) {
                throw new RuntimeException("Project name can't be empty");
            }
            String sourceProjectName = AppUtil.getSourceProjectName(name);
            String projectName = AppUtil.getProjectName(sourceProjectName);
            String projectVersion = AppUtil.getUpdatedValue(projectDetail.getProjectVersion(), projectConfig.getJarVersion());
            String projectDir = baseProjectDir + File.separator + projectName;
            String packageName = AppUtil.getPackageName(projectName, projectConfig.getBasePackageName(), projectDetail);
            String packageDir = packageName.replace(DOT_DELIMITER, "/");
            String delimiter = projectDetail.getDelimiter();
            String className = sourceProjectName + "App";
            String javaClassName = sourceProjectName + "JavaApp";
            String jarVersion = projectConfig.getJarVersion();
            String jarName = projectName + "-" + jarVersion + ".jar";
            String fullClassName = packageName + DOT_DELIMITER + className;
            String jarDeployPath = baseDeployJarPath + projectName;
            String jarPath = jarDeployPath + File.separator + jarName;
            String runScriptName = "run_" + projectName.replace(delimiter, "_") + "_app.sh";
            String runScriptPath = projectDir + File.separator + runScriptName;
            String readMePath = projectDir + File.separator + README_FILE;
            String deployScriptPath = jarDeployPath + File.separator + runScriptName;
            String repoName = AppUtil.getRepositoryNames(projectConfig);

            ProjectInfoBean projectInfoBean = new ProjectInfoBean();
            projectInfoBean.setProjectName(projectName);
            projectInfoBean.setName(name);
            projectInfoBean.setSourceProjectName(sourceProjectName);
            projectInfoBean.setProjectVersion(projectVersion);
            projectInfoBean.setScalaVersion(scalaVersion);
            projectInfoBean.setJavaVersion(javaVersion);
            projectInfoBean.setScalaBinaryVersion(scalaBinaryVersion);
            projectInfoBean.setProjectDirectory(projectDir);
            projectInfoBean.setJarVersion(jarVersion);
            projectInfoBean.setPackageName(packageName);
            projectInfoBean.setDelimiter(delimiter);
            projectInfoBean.setPackageDir(packageDir);
            projectInfoBean.setBaseDeployJarPath(baseDeployJarPath);
            projectInfoBean.setTemplateType(templateType);
            projectInfoBean.setClassName(className);
            projectInfoBean.setJavaClassName(javaClassName);
            projectInfoBean.setFullClassName(fullClassName);
            projectInfoBean.setJarName(jarName);
            projectInfoBean.setJarDeployPath(jarDeployPath);
            projectInfoBean.setJarPath(jarPath);
            projectInfoBean.setRunScriptName(runScriptName);
            projectInfoBean.setRunScriptPath(runScriptPath);
            projectInfoBean.setReadMePath(readMePath);
            projectInfoBean.setDeployScriptPath(deployScriptPath);
            projectInfoBean.setRepoName(repoName);
            projectInfoBean.setSecureCluster(isSecureCluster);
            projectInfoBean.setSSLCluster(isSSLCluster);
            projectInfoBean.setAuthor(projectConfig.getAuthor());
            projectInfoBean.setCreatedDate(createdDate);
            TemplateUtil.buildTemplates(projectConfig, projectInfoBean, projectConfigMap);
            projectInfoBeanList.add(projectInfoBean);
        }
        return projectInfoBeanList;
    }
}