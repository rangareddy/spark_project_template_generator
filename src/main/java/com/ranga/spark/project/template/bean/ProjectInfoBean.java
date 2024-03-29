package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.util.TemplateType;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectInfoBean implements Serializable {

    private String projectName;
    private String projectDescription;
    private String projectDirectory;
    private String sourceProjectName;
    private String name;
    private String projectVersion;
    private String scalaVersion;
    private String scalaBinaryVersion;
    private String packageName;
    private String className;
    private String javaClassName;
    private String fullClassName;
    private String jarName;
    private String jarPath;
    private String jarVersion;
    private boolean isJavaTemplate;
    private String mvnRepoName;
    private String runScriptName;
    private String runScriptPath;
    private String readMePath;
    private String jarDeployPath;
    private String deployScriptPath;
    private TemplateType templateType;
    private String packageDir;
    private String baseDeployJarPath;
    private String delimiter;
    private String prerequisites;
    private String javaVersion;
    private String sparkVersion;
    private String setUpInstructions = "";
    private CodeTemplateBean scalaCodeTemplate;
    private CodeTemplateBean javaCodeTemplate;
    private boolean mavenBuildTool;
    private boolean sbtBuildTool;
    private MavenBuildToolBean mavenBuildToolBean;
    private SbtBuildToolBean sbtBuildToolBean;
    private String sparkSubmitCommand;
    private String runScriptArguments = "";
    private List<String> runScriptNotesList = new ArrayList<>();
    private boolean isSecureCluster;
    private boolean isSSLCluster;
    private String author;
    private String authorId;
    private String createdDate;
    private String mainMethodArguments = "";
    private boolean isCreateScalaBeanClass;
    private boolean isCreateJavaBeanClass;
    private String integrationImg = "";
    private String authorEmail;
    private String sbtRepoName;
    private boolean buildLocally;
    private String aboutTemplate;
}