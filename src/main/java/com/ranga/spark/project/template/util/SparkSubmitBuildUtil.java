package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.bean.SparkSubmitBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SparkSubmitBuildUtil {
    public static void buildSparkSubmit(SparkSubmitBean sparkSubmitBean, ProjectInfoBean projectInfoBean) {
        sparkSubmitBean.setName(projectInfoBean.getSourceProjectName());
        sparkSubmitBean.setClassName(projectInfoBean.getFullClassName());
        sparkSubmitBean.setJarPath(projectInfoBean.getJarPath());

        String filesInfo = "";
        List<String> filesList = sparkSubmitBean.getFileList();
        if(CollectionUtils.isNotEmpty(filesList)) {
            filesInfo = String.join(",", sparkSubmitBean.getFileList());
        }
        sparkSubmitBean.setFiles(filesInfo);

        List<String> argumentList = sparkSubmitBean.getArgumentList();
        String argumentsVar = "";
        StringBuilder arguments = new StringBuilder();
        if(CollectionUtils.isNotEmpty(argumentList)) {
            String argumentsUsage = String.join(" ", argumentList).toUpperCase();
            argumentsVar  = "if [ $# -lt "+argumentList.size()+" ]; then\n" +
                    "    echo \"Usage   : $0 "+argumentsUsage+"\"\n" +
                    "    echo \" \"\n" +
                    "    exit 1\n" +
                    "fi\n";

            for(int i=0; i<argumentList.size(); i++) {
                arguments.append("$").append(i+1).append(" ");
            }
        }
        projectInfoBean.setRunScriptArguments(argumentsVar);

        List<String> secureArgumentList = sparkSubmitBean.getSecureArgumentList();
        List<String> totalArguments = new ArrayList<>(secureArgumentList);
        totalArguments.addAll(argumentList);

        String secArgumentsVar = "";
        StringBuilder secArguments = new StringBuilder();
        if(CollectionUtils.isNotEmpty(totalArguments)) {
            String secArgumentsUsage = String.join(" ", totalArguments).toUpperCase();
            secArgumentsVar = "if [ $# -lt " + totalArguments.size() + " ]; then\n" +
                    "    echo \"Usage   : $0 " + secArgumentsUsage + "\"\n" +
                    "    echo \" \"\n" +
                    "    exit 1\n" +
                    "fi\n";

            for(int i=2; i<totalArguments.size(); i++) {
                secArguments.append("$").append(i+1).append(" ");
            }
        }
        projectInfoBean.setRunScriptSecArguments(secArgumentsVar);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("spark-submit").append(" \\\n");
        stringBuilder.append("  --name ").append(sparkSubmitBean.getName()).append(" \\\n");
        stringBuilder.append("  --master ").append(sparkSubmitBean.getMaster()).append(" \\\n");
        stringBuilder.append("  --deploy-mode ").append(sparkSubmitBean.getDeployMode()).append(" \\\n");
        stringBuilder.append("  --driver-memory ").append(sparkSubmitBean.getDriverMemory()).append(" \\\n");
        stringBuilder.append("  --executor-memory ").append(sparkSubmitBean.getExecutorMemory()).append(" \\\n");
        stringBuilder.append("  --num-executors ").append(sparkSubmitBean.getNumExecutors()).append(" \\\n");
        stringBuilder.append("  --driver-cores ").append(sparkSubmitBean.getDriverCores()).append(" \\\n");
        stringBuilder.append("  --executor-cores ").append(sparkSubmitBean.getExecutorCores()).append(" \\\n");
        stringBuilder.append("SECURITY_INFO FILES_INFO");
        stringBuilder.append("  --class ").append(sparkSubmitBean.getClassName()).append(" \\\n");;
        stringBuilder.append("  ").append(sparkSubmitBean.getJarPath()).append(" ").append("ARGUMENTS");
        String filesInfoStr = "";
        if(StringUtils.isNotEmpty(filesInfo)) {
            filesInfoStr = " --files " + filesInfo +" \\\n";
        }

        String sparkSubmitCommand = getSubmitCommand(stringBuilder, "", filesInfoStr, arguments.toString());
        projectInfoBean.setSparkSubmitCommand(sparkSubmitCommand);

        String securityInfo = "  --principal $PRINCIPAL \\\n  --keytab $KEYTAB \\\n";
        String secureSubmitCommand = getSubmitCommand(stringBuilder, securityInfo, filesInfoStr, secArguments.toString());
        projectInfoBean.setSparkSubmitSecureCommand(secureSubmitCommand);
    }

    private static String getSubmitCommand(StringBuilder command, String securityInfo, String filesInfo, String argumentsInfo) {
        return command.toString()
                .replace("SECURITY_INFO", securityInfo)
                .replace("FILES_INFO", filesInfo)
                .replace("ARGUMENTS", argumentsInfo).trim();
    }
}