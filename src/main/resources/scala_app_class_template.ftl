package ${projectBuilder.packageName}

${projectBuilder.scalaCodeTemplate.importTemplate}

case class Employee(id:Long, name: String, age: Integer, salary: Float)

${projectBuilder.scalaCodeTemplate.classTemplate} {

    @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

    def main(args: Array[String]): Unit = {
        ${projectBuilder.scalaCodeTemplate.sparkSessionBuildTemplate}

        ${projectBuilder.scalaCodeTemplate.codeTemplate}

        logger.info("${projectBuilder.projectName} application processing finished")

        ${projectBuilder.scalaCodeTemplate.sparkSessionCloseTemplate}
    }

    ${projectBuilder.scalaCodeTemplate.methodsTemplate}
}