package ${projectBuilder.packageName};

${projectBuilder.javaCodeTemplate.importTemplate}

/**
 * @author ${projectBuilder.author}
 * Version: 1.0
 * Created : ${projectBuilder.createdDate}
 */

${projectBuilder.javaCodeTemplate.classTemplate} {

    private static final Logger logger = Logger.getLogger(${projectBuilder.javaClassName}.class.getName());

    public static void main(String[] args) {
${projectBuilder.mainMethodArguments}
        String appName = "${projectBuilder.name}";
        ${projectBuilder.javaCodeTemplate.sparkSessionBuildTemplate}

        ${projectBuilder.javaCodeTemplate.codeTemplate}

        logger.info("<${projectBuilder.name}> successfully finished");

        ${projectBuilder.javaCodeTemplate.sparkSessionCloseTemplate}
    }

    ${projectBuilder.javaCodeTemplate.methodsTemplate}
}