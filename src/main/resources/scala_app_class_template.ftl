package ${projectBuilder.packageName}

${projectBuilder.properties.importTemplate}

object ${projectBuilder.className} extends App with Serializable {

    ${projectBuilder.properties.sparkSessionBuildTemplate}

    ${projectBuilder.properties.codeTemplate}

    logger.info("${projectBuilder.appName} Finished")

    ${projectBuilder.properties.sparkSessionCloseTemplate}
}