# ${projectBuilder.sourceProjectName}

<div>
${projectBuilder.integrationImg}
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/>

## Prerequisites

<#list projectBuilder.prerequisitesList as prerequisite>
* ${prerequisite}
</#list>

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
```sh
$ mvn clean package -DskipTests
```

## Copy the `${projectBuilder.jarName}` uber jar and run script `${projectBuilder.runScriptName}` to spark gateway node `${projectBuilder.jarDeployPath}` directory.
```sh
$ scp target/${projectBuilder.jarName} username@mynode.host.com:${projectBuilder.jarDeployPath}
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