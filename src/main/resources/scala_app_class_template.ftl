package ${projectBuilder.packageName}

${projectBuilder.properties.importTemplate}

${projectBuilder.properties.classTemplate} {

    ${projectBuilder.properties.sparkSessionBuildTemplate}

    ${projectBuilder.properties.codeTemplate}

    logger.info("${projectBuilder.appName} Finished")

    ${projectBuilder.properties.sparkSessionCloseTemplate}
}