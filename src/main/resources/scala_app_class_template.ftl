package ${projectBuilder.packageName}

${projectBuilder.properties.importTemplate}

case class Employee(id:Long, name: String, age: Integer, salary: Float)

${projectBuilder.properties.classTemplate} {

    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

    def main(args: Array[String]): Unit = {
        ${projectBuilder.properties.sparkSessionBuildTemplate}

        ${projectBuilder.properties.codeTemplate}

        logger.info("${projectBuilder.appName} application processing finished")

        ${projectBuilder.properties.sparkSessionCloseTemplate}
    }

    ${projectBuilder.properties.methodsTemplate}
}