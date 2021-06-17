package ${projectBuilder.packageName};

${projectBuilder.properties.importJavaTemplate}

${projectBuilder.properties.classJavaTemplate} {

    private static final Logger logger = Logger.getLogger(${projectBuilder.javaClassName}.class.getName());

    public static void main(String[] args) {

        ${projectBuilder.properties.sparkSessionBuildJavaTemplate}

        ${projectBuilder.properties.codeJavaTemplate}

        logger.info("${projectBuilder.appName} Finished");

        ${projectBuilder.properties.sparkSessionCloseJavaTemplate}
    }

}