package com.ranga.spark.project.template.util;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.java.DefaultJavaTemplate;
import com.ranga.spark.project.template.api.java.HWCJavaTemplate;
import com.ranga.spark.project.template.api.scala.DefaultTemplate;
import com.ranga.spark.project.template.api.scala.HBaseTemplate;
import com.ranga.spark.project.template.api.scala.HWCTemplate;
import com.ranga.spark.project.template.api.scala.HiveTemplate;
import com.ranga.spark.project.template.bean.*;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.*;

public class TemplateUtil implements Serializable {

    public static CodeTemplateBean getCodeTemplateBean(BaseTemplate template) {
        CodeTemplateBean codeTemplateBean = new CodeTemplateBean();
        codeTemplateBean.setCodeTemplate(template.codeTemplate());
        codeTemplateBean.setClassTemplate(template.classTemplate());
        codeTemplateBean.setImportTemplate(template.importTemplate());
        codeTemplateBean.setMethodsTemplate(template.methodsTemplate());
        codeTemplateBean.setSparkSessionBuildTemplate(template.sparkSessionBuildTemplate());
        codeTemplateBean.setSparkSessionCloseTemplate(template.sparkSessionCloseTemplate());
        return codeTemplateBean;
    }

    public static TemplateType getTemplateType(String templateTypeName) {
        TemplateType[] templateTypes = TemplateType.values();
        TemplateType templateType = null;
        for (TemplateType tType : templateTypes) {
            if (tType.name().equals(templateTypeName.toUpperCase())) {
                templateType = tType;
                break;
            }
        }
        if (templateType == null) {
            throw new RuntimeException("TemplateType '" + templateTypeName + "' not found");
        }
        return templateType;
    }

    public static void buildTemplates(ProjectConfig projectConfig, ProjectInfoBean projectInfoBean, Map<String, String> projectConfigMap) {
        TemplateType templateType = projectInfoBean.getTemplateType();
        BaseTemplate template;
        BaseTemplate javaTemplate = null;
        String className = projectInfoBean.getClassName();
        String javaClassName = projectInfoBean.getJavaClassName();
        List<DependencyBean> defaultTemplateDependency = projectConfig.getDefaultTemplate();
        List<DependencyBean> othersTemplatesDependency = Collections.emptyList();
        SparkSubmitBean sparkSubmitBean = new SparkSubmitBean();

        List<String> argumentList = new ArrayList<>();
        if (projectInfoBean.isSecureCluster()) {
            List<String> secureArgumentList = new ArrayList<>();
            secureArgumentList.add("PRINCIPAL");
            secureArgumentList.add("KEYTAB");
            sparkSubmitBean.setSecureArgumentList(secureArgumentList);
        }

        String setupInstructions = "";
        Map<String, String> othersConfMap = new LinkedHashMap<>();
        List<String> runScriptNotesList = projectInfoBean.getRunScriptNotesList();
        switch (templateType) {
            case HBASE:
                template = new HBaseTemplate(className);
                othersTemplatesDependency = projectConfig.getHbaseTemplate();
                setupInstructions = template.setupInstructions();
                break;
            case HIVE:
                template = new HiveTemplate(className);
                othersTemplatesDependency = projectConfig.getHiveTemplate();
                break;
            case HWC:

                runScriptNotesList.add("Update `hiveserver2_host` in `spark.sql.hive.hiveserver2.jdbc.url`");
                runScriptNotesList.add("Update `metastore_uri` in `spark.hadoop.hive.metastore.uris`");

                argumentList.add("HIVE_SERVER2_HOST");
                argumentList.add("METASTORE_URI");

                othersConfMap.put("spark.sql.hive.hiveserver2.jdbc.url", "jdbc:hive2://${HIVE_SERVER2_HOST}:10000");
                othersConfMap.put("spark.hadoop.hive.metastore.uris", "thrift://${METASTORE_URI}:9083");
                othersConfMap.put("spark.sql.hive.hwc.execution.mode", "spark");
                othersConfMap.put("spark.datasource.hive.warehouse.load.staging.dir", "/tmp");
                othersConfMap.put("spark.datasource.hive.warehouse.read.via.llap", "false");
                othersConfMap.put("spark.datasource.hive.warehouse.read.jdbc.mode", "cluster");
                othersConfMap.put("spark.datasource.hive.warehouse.read.mode", "DIRECT_READER_V1");
                othersConfMap.put("spark.kryo.registrator", "com.qubole.spark.hiveacid.util.HiveAcidKyroRegistrator");
                othersConfMap.put("spark.sql.extensions", "com.hortonworks.spark.sql.rule.Extensions");

                if (projectInfoBean.isSecureCluster()) {
                    argumentList.add("HIVE_SERVER2_AUTH_KERBEROS_PRINCIPAL");
                    runScriptNotesList.add("Update `hive.server2.authentication.kerberos.principal` in `spark.sql.hive.hiveserver2.jdbc.url.principal`");
                    othersConfMap.put("spark.security.credentials.hiveserver2.enabled", "true");
                    othersConfMap.put("spark.sql.hive.hiveserver2.jdbc.url.principal", "${HIVE_SERVER2_AUTH_KERBEROS_PRINCIPAL}");
                } else {
                    othersConfMap.put("spark.security.credentials.hiveserver2.enabled", "false");
                    othersConfMap.put("spark.datasource.hive.warehouse.user.name", "hive");
                    othersConfMap.put("spark.datasource.hive.warehouse.password", "hive");
                }

                template = new HWCTemplate(className);
                javaTemplate = new HWCJavaTemplate(javaClassName);
                othersTemplatesDependency = projectConfig.getHwcTemplate();
                break;
            default:
                template = new DefaultTemplate(className);
                javaTemplate = new DefaultJavaTemplate(javaClassName);
        }
        projectInfoBean.setRunScriptNotesList(runScriptNotesList);
        sparkSubmitBean.setOtherConfMap(othersConfMap);
        sparkSubmitBean.setArgumentList(argumentList);
        CodeTemplateBean codeTemplateBean = TemplateUtil.getCodeTemplateBean(template);
        projectInfoBean.setScalaCodeTemplate(codeTemplateBean);
        projectInfoBean.setSetUpInstructions(setupInstructions);

        if (javaTemplate != null) {
            projectInfoBean.setJavaTemplate(true);
            codeTemplateBean = TemplateUtil.getCodeTemplateBean(javaTemplate);
            projectInfoBean.setJavaCodeTemplate(codeTemplateBean);
        }

        Set<DependencyBean> dependencyBeanSet = new LinkedHashSet<>(defaultTemplateDependency);
        if (CollectionUtils.isNotEmpty(othersTemplatesDependency)) {
            dependencyBeanSet.addAll(othersTemplatesDependency);
        }
        AppUtil.buildDependencies(projectConfig.getBuildTools(), dependencyBeanSet, projectInfoBean, projectConfigMap);
        SparkSubmitBuildUtil.buildSparkSubmit(sparkSubmitBean, projectInfoBean);
    }
}