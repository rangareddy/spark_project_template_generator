package ${projectBuilder.packageName}

${projectBuilder.scalaCodeTemplate.importTemplate}

/**
 * @author ${projectBuilder.author}
 * Version: 1.0
 * Created : ${projectBuilder.createdDate}
 */

${projectBuilder.scalaCodeTemplate.classTemplate} {

    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

    def main(args: Array[String]): Unit = {
        ${projectBuilder.mainMethodArguments}

        val appName = "${projectBuilder.name}"
        ${projectBuilder.scalaCodeTemplate.sparkSessionBuildTemplate}

        ${projectBuilder.scalaCodeTemplate.codeTemplate}

        logger.info("<${projectBuilder.name}> successfully finished")

        ${projectBuilder.scalaCodeTemplate.sparkSessionCloseTemplate}
    }

    ${projectBuilder.scalaCodeTemplate.methodsTemplate}
}