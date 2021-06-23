package ${projectBuilder.packageName};

${projectBuilder.javaCodeTemplate.importTemplate}

${projectBuilder.javaCodeTemplate.classTemplate} {

    private static final Logger logger = Logger.getLogger(${projectBuilder.javaClassName}.class.getName());

    public static void main(String[] args) {

        ${projectBuilder.javaCodeTemplate.sparkSessionBuildTemplate}

        ${projectBuilder.javaCodeTemplate.codeTemplate}

        logger.info("${projectBuilder.projectName} application processing finished");

        ${projectBuilder.javaCodeTemplate.sparkSessionCloseTemplate}
    }

    ${projectBuilder.javaCodeTemplate.methodsTemplate}
}