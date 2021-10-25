# ${projectBuilder.name}

${projectBuilder.integrationImg}

${projectBuilder.prerequisites}

${projectBuilder.setUpInstructions}

## Login to spark gateway node (for example mynode.host.com) and create the application deployment directory `${projectBuilder.jarDeployPath}`.
```sh
$ ssh username@mynode.host.com
$ mkdir -p ${projectBuilder.jarDeployPath}
$ chmod 755 ${projectBuilder.jarDeployPath}
```

## Download the `${projectBuilder.projectName}` application.
```sh
$ git clone https://github.com/rangareddy/ranga_spark_experiments.git
$ cd ranga_spark_experiments/${projectBuilder.projectName}
```

## Build the `${projectBuilder.projectName}` application.
**Note:** Before building the application, update spark & other components library versions according to your cluster version.

<#if projectBuilder.isMavenBuildTool>
### 1) Building the project using maven build tool

```sh
$ mvn clean package
```

### 2) Copy the `${projectBuilder.jarName}` uber jar to spark gateway node `${projectBuilder.jarDeployPath}` directory.

```sh
$ scp target/${projectBuilder.jarName} username@mynode.host.com:${projectBuilder.jarDeployPath}
```
</#if>

<#if projectBuilder.isSbtBuildTool>
### 1) Building the project using sbt build tool

```sh
$ sbt clean package
```

### 2) Copy the `${projectBuilder.jarName}` uber jar to spark gateway node `${projectBuilder.jarDeployPath}` directory.

```sh
$ scp target/${projectBuilder.scalaBinaryVersion}/${projectBuilder.jarName} username@mynode.host.com:${projectBuilder.jarDeployPath}
```
</#if>

## Copy the run script `${projectBuilder.runScriptName}` to spark gateway node `${projectBuilder.jarDeployPath}` directory.

```sh
$ scp ${projectBuilder.runScriptName} username@mynode.host.com:${projectBuilder.jarDeployPath}
```

## Login to spark gateway node (for example mynode.host.com) and run the application using `${projectBuilder.runScriptName}` shell script.

**Note(s):**
* Before running the application, check do you have proper permissions to run the application.
* Check is there any parameters needs to pass in `${projectBuilder.runScriptName}` shell script.
<#list projectBuilder.runScriptNotesList as runScriptNote>
* ${runScriptNote}
</#list>

```sh
sh ${projectBuilder.deployScriptPath}
```