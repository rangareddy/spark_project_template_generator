package com.ranga.spark.project.template.bean;

import com.ranga.spark.project.template.util.TemplateType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ProjectInfoBean implements Serializable {

    private String projectName;
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
    private String repoName;
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
    private List<String> prerequisitesList;
    private String setUpInstructions = "";
    private CodeTemplateBean scalaCodeTemplate;
    private CodeTemplateBean javaCodeTemplate;
    private MavenBuildToolBean mavenBuildToolBean;
    private SbtBuildToolBean sbtBuildToolBean;
    private String sparkSubmitCommand;
    private String runScriptArguments;
    private String runScriptSecArguments;
    private List<String> runScriptNotesList = new ArrayList<>();
    private boolean isSecureCluster;
    private boolean isSSLCluster;
    private String author;
    private String createdDate;
    private String mainMethodArguments = "";

    public ProjectInfoBean() {

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public List<String> getPrerequisitesList() {
        return prerequisitesList;
    }

    public void setPrerequisitesList(List<String> prerequisitesList) {
        this.prerequisitesList = prerequisitesList;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getScalaVersion() {
        return scalaVersion;
    }

    public void setScalaVersion(String scalaVersion) {
        this.scalaVersion = scalaVersion;
    }

    public String getScalaBinaryVersion() {
        return scalaBinaryVersion;
    }

    public void setScalaBinaryVersion(String scalaBinaryVersion) {
        this.scalaBinaryVersion = scalaBinaryVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getJarName() {
        return jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getJarVersion() {
        return jarVersion;
    }

    public void setJarVersion(String jarVersion) {
        this.jarVersion = jarVersion;
    }

    public boolean isJavaTemplate() {
        return isJavaTemplate;
    }

    public void setJavaTemplate(boolean javaTemplate) {
        isJavaTemplate = javaTemplate;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getRunScriptName() {
        return runScriptName;
    }

    public void setRunScriptName(String runScriptName) {
        this.runScriptName = runScriptName;
    }

    public String getRunScriptPath() {
        return runScriptPath;
    }

    public void setRunScriptPath(String runScriptPath) {
        this.runScriptPath = runScriptPath;
    }

    public String getReadMePath() {
        return readMePath;
    }

    public void setReadMePath(String readMePath) {
        this.readMePath = readMePath;
    }

    public String getJarDeployPath() {
        return jarDeployPath;
    }

    public void setJarDeployPath(String jarDeployPath) {
        this.jarDeployPath = jarDeployPath;
    }

    public String getDeployScriptPath() {
        return deployScriptPath;
    }

    public void setDeployScriptPath(String deployScriptPath) {
        this.deployScriptPath = deployScriptPath;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    public String getSourceProjectName() {
        return sourceProjectName;
    }

    public void setSourceProjectName(String sourceProjectName) {
        this.sourceProjectName = sourceProjectName;
    }

    public String getProjectDirectory() {
        return projectDirectory;
    }

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public String getPackageDir() {
        return packageDir;
    }

    public void setPackageDir(String packageDir) {
        this.packageDir = packageDir;
    }

    public String getBaseDeployJarPath() {
        return baseDeployJarPath;
    }

    public void setBaseDeployJarPath(String baseDeployJarPath) {
        this.baseDeployJarPath = baseDeployJarPath;
    }

    public MavenBuildToolBean getMavenBuildToolBean() {
        return mavenBuildToolBean;
    }

    public void setMavenBuildToolBean(MavenBuildToolBean mavenBuildToolBean) {
        this.mavenBuildToolBean = mavenBuildToolBean;
    }

    public SbtBuildToolBean getSbtBuildToolBean() {
        return sbtBuildToolBean;
    }

    public void setSbtBuildToolBean(SbtBuildToolBean sbtBuildToolBean) {
        this.sbtBuildToolBean = sbtBuildToolBean;
    }

    public CodeTemplateBean getScalaCodeTemplate() {
        return scalaCodeTemplate;
    }

    public void setScalaCodeTemplate(CodeTemplateBean scalaCodeTemplate) {
        this.scalaCodeTemplate = scalaCodeTemplate;
    }

    public CodeTemplateBean getJavaCodeTemplate() {
        return javaCodeTemplate;
    }

    public void setJavaCodeTemplate(CodeTemplateBean javaCodeTemplate) {
        this.javaCodeTemplate = javaCodeTemplate;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getSetUpInstructions() {
        return setUpInstructions;
    }

    public void setSetUpInstructions(String setUpInstructions) {
        this.setUpInstructions = setUpInstructions;
    }

    public String getSparkSubmitCommand() {
        return sparkSubmitCommand;
    }

    public void setSparkSubmitCommand(String sparkSubmitCommand) {
        this.sparkSubmitCommand = sparkSubmitCommand;
    }

    public String getRunScriptArguments() {
        return runScriptArguments;
    }

    public void setRunScriptArguments(String runScriptArguments) {
        this.runScriptArguments = runScriptArguments;
    }

    public String getRunScriptSecArguments() {
        return runScriptSecArguments;
    }

    public void setRunScriptSecArguments(String runScriptSecArguments) {
        this.runScriptSecArguments = runScriptSecArguments;
    }

    public boolean isSecureCluster() {
        return isSecureCluster;
    }

    public void setSecureCluster(boolean isSecureCluster) {
        this.isSecureCluster = isSecureCluster;
    }

    public List<String> getRunScriptNotesList() {
        return runScriptNotesList;
    }

    public void setRunScriptNotesList(List<String> runScriptNotesList) {
        this.runScriptNotesList = runScriptNotesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSSLCluster() {
        return isSSLCluster;
    }

    public void setSSLCluster(boolean SSLCluster) {
        isSSLCluster = SSLCluster;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getMainMethodArguments() {
        return mainMethodArguments;
    }

    public void setMainMethodArguments(String mainMethodArguments) {
        this.mainMethodArguments = mainMethodArguments;
    }

    @Override
    public String toString() {
        return "ProjectInfoBean{" +
                "projectName='" + projectName + '\'' +
                ", projectDirectory='" + projectDirectory + '\'' +
                ", sourceProjectName='" + sourceProjectName + '\'' +
                ", name='" + name + '\'' +
                ", projectVersion='" + projectVersion + '\'' +
                ", scalaVersion='" + scalaVersion + '\'' +
                ", scalaBinaryVersion='" + scalaBinaryVersion + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", javaClassName='" + javaClassName + '\'' +
                ", fullClassName='" + fullClassName + '\'' +
                ", jarName='" + jarName + '\'' +
                ", jarPath='" + jarPath + '\'' +
                ", jarVersion='" + jarVersion + '\'' +
                ", isJavaTemplate=" + isJavaTemplate +
                ", repoName='" + repoName + '\'' +
                ", runScriptName='" + runScriptName + '\'' +
                ", runScriptPath='" + runScriptPath + '\'' +
                ", readMePath='" + readMePath + '\'' +
                ", jarDeployPath='" + jarDeployPath + '\'' +
                ", deployScriptPath='" + deployScriptPath + '\'' +
                ", templateType=" + templateType +
                ", packageDir='" + packageDir + '\'' +
                ", baseDeployJarPath='" + baseDeployJarPath + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", prerequisites='" + prerequisites + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", prerequisitesList=" + prerequisitesList +
                ", setUpInstructions='" + setUpInstructions + '\'' +
                ", scalaCodeTemplate=" + scalaCodeTemplate +
                ", javaCodeTemplate=" + javaCodeTemplate +
                ", mavenBuildToolBean=" + mavenBuildToolBean +
                ", sbtBuildToolBean=" + sbtBuildToolBean +
                ", sparkSubmitCommand='" + sparkSubmitCommand + '\'' +
                ", runScriptArguments='" + runScriptArguments + '\'' +
                ", runScriptSecArguments='" + runScriptSecArguments + '\'' +
                ", runScriptNotesList=" + runScriptNotesList +
                ", isSecureCluster=" + isSecureCluster +
                ", isSSLCluster=" + isSSLCluster +
                ", author='" + author + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", mainMethodArguments='" + mainMethodArguments + '\'' +
                '}';
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}