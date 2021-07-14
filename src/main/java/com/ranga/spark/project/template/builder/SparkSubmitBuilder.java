package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.bean.SparkSubmitBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SparkSubmitBuilder {

    public static void buildSparkSubmit(SparkSubmitBean sparkSubmitBean, ProjectInfoBean projectInfoBean) {

        sparkSubmitBean.setName(projectInfoBean.getSourceProjectName());
        sparkSubmitBean.setClassName(projectInfoBean.getFullClassName());
        sparkSubmitBean.setJarPath(projectInfoBean.getJarPath());

        String filesInfo = getFilesInfo(sparkSubmitBean);
        sparkSubmitBean.setFiles(filesInfo);

        List<String> usageArgumentList = sparkSubmitBean.getUsageArgumentList();
        List<String> appArgumentList = sparkSubmitBean.getAppArgumentList();
        List<String> totalArguments = new ArrayList<>(usageArgumentList);
        if(projectInfoBean.isSecureCluster()) {
            totalArguments.addAll(sparkSubmitBean.getSecureArgumentList());
        }

        StringBuilder usageSB = new StringBuilder();
        // Building Scala/Java main method arguments
        if(CollectionUtils.isNotEmpty(appArgumentList)) {
            int size = appArgumentList.size();
            StringBuilder mainMethodArguments = new StringBuilder("\n");
            mainMethodArguments.append("        if(args.length > ").append(size).append(" ) {\n");
            mainMethodArguments.append("            System.err.println(\"");
            mainMethodArguments.append("Usage : "+projectInfoBean.getClassName());
            for(int i=0; i< size; i++) {
                String argumentName = appArgumentList.get(i);
                mainMethodArguments.append(" <").append(argumentName).append(">");
                usageSB.append("${").append(argumentName).append("}");
                if(i != size - 1) {
                    usageSB.append(",");
                }
            }
            mainMethodArguments.append("\");\n");
            mainMethodArguments.append("            System.exit(0);\n");
            mainMethodArguments.append("        }");
            projectInfoBean.setMainMethodArguments(mainMethodArguments.toString());
        }

        if(CollectionUtils.isNotEmpty(totalArguments)) {
            StringBuilder argumentsUsage = new StringBuilder();
            StringBuilder varArgsSB = new StringBuilder();
            for(int i=0; i< totalArguments.size(); i++) {
                String argument = totalArguments.get(i).toUpperCase();
                argumentsUsage.append("<").append(argument).append("> ");
                varArgsSB.append(argument).append("=$").append(i + 1).append("\n");
            }
            String usageArgumentsStr = "if [ $# -lt " + totalArguments.size() + " ]; then\n" +
                    "    echo \"Usage   : $0 " + argumentsUsage.toString().trim() + "\"\n" +
                    "    echo \" \"\n" +
                    "    exit 1\n" +
                    "fi\n";
            usageArgumentsStr = usageArgumentsStr + "\n"+ varArgsSB;
            projectInfoBean.setRunScriptArguments(usageArgumentsStr);
        }

        Map<String, String> optionsMap = new LinkedHashMap<>();
        optionsMap.put("spark.app.name", sparkSubmitBean.getName());
        optionsMap.put("spark.master", sparkSubmitBean.getMaster());
        optionsMap.put("spark.submit.deployMode", sparkSubmitBean.getDeployMode());
        optionsMap.put("spark.driver.memory", sparkSubmitBean.getDriverMemory());
        optionsMap.put("spark.executor.memory", sparkSubmitBean.getExecutorMemory());
        optionsMap.put("spark.driver.cores", sparkSubmitBean.getDriverCores());
        optionsMap.put("spark.executor.cores", sparkSubmitBean.getExecutorCores());
        optionsMap.put("spark.executor.instances", sparkSubmitBean.getNumExecutors());

        optionsMap.putAll(sparkSubmitBean.getOtherConfMap());

        StringBuilder stringBuilder = new StringBuilder("spark-submit \\\n");
        for (Map.Entry<String, String> optionsEntry : optionsMap.entrySet()) {
            String optionKey = optionsEntry.getKey();
            String optionValue = optionsEntry.getValue();
            stringBuilder.append("\t--conf ").append(optionKey).append("=").append(optionValue).append(" \\\n");
        }

        stringBuilder.append("SECURITY_INFO FILES_INFO");
        stringBuilder.append("\t--class ").append(sparkSubmitBean.getClassName()).append(" \\\n");
        stringBuilder.append("\t").append(sparkSubmitBean.getJarPath()).append(" ").append("ARGUMENTS");
        String filesInfoStr = StringUtils.isNotEmpty(filesInfo) ? "\t--files " + filesInfo + " \\\n" : "";
        String securityInfo = projectInfoBean.isSecureCluster() ? "\t--principal ${PRINCIPAL} \\\n\t--keytab ${KEYTAB} \\\n" : "";
        String sparkSubmitCommand = getSubmitCommand(stringBuilder, securityInfo, filesInfoStr, usageSB.toString());
        projectInfoBean.setSparkSubmitCommand(sparkSubmitCommand);
    }

    private static String getFilesInfo(SparkSubmitBean sparkSubmitBean) {
        String filesInfo = "";
        List<String> filesList = sparkSubmitBean.getFileList();
        if (CollectionUtils.isNotEmpty(filesList)) {
            filesInfo = String.join(",", sparkSubmitBean.getFileList());
        }
        return filesInfo;
    }

    private static String getSubmitCommand(StringBuilder command, String securityInfo, String filesInfo, String argumentsInfo) {
        return command.toString()
                .replace("SECURITY_INFO", securityInfo)
                .replace("FILES_INFO", filesInfo)
                .replace("ARGUMENTS", argumentsInfo).trim();
    }
}