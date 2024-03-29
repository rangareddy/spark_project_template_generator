package com.ranga.spark.project.template.builder;

import com.ranga.spark.project.template.api.BaseTemplate;
import com.ranga.spark.project.template.api.java.DefaultJavaTemplate;
import com.ranga.spark.project.template.api.java.HWCJavaTemplate;
import com.ranga.spark.project.template.api.java.fileformats.AvroFileFormatJavaTemplate;
import com.ranga.spark.project.template.api.java.fileformats.OrcFileFormatsJavaTemplate;
import com.ranga.spark.project.template.api.java.fileformats.ParquetFileFormatJavaTemplate;
import com.ranga.spark.project.template.api.scala.*;
import com.ranga.spark.project.template.api.scala.cloud.GcsTemplate;
import com.ranga.spark.project.template.api.scala.cloud.S3Template;
import com.ranga.spark.project.template.api.scala.fileformats.AvroFileFormatTemplate;
import com.ranga.spark.project.template.api.scala.fileformats.OrcFileFormatTemplate;
import com.ranga.spark.project.template.api.scala.fileformats.ParquetFileFormatTemplate;
import com.ranga.spark.project.template.bean.CodeTemplateBean;
import com.ranga.spark.project.template.bean.ProjectConfig;
import com.ranga.spark.project.template.bean.ProjectInfoBean;
import com.ranga.spark.project.template.bean.SparkSubmitBean;
import com.ranga.spark.project.template.util.AppUtil;
import com.ranga.spark.project.template.util.TemplateType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings({"unused", "raw"})
public class TemplateBuilder implements Serializable {

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

    public static void buildTemplates(ProjectConfig projectConfig, ProjectInfoBean projectInfoBean,
                                      Map<String, String> projectConfigMap) {
        TemplateType templateType = projectInfoBean.getTemplateType();
        BaseTemplate template, javaTemplate = null;
        String className = projectInfoBean.getClassName();
        String javaClassName = projectInfoBean.getJavaClassName();
        String templateName = templateType.name().toLowerCase();
        Map<String, List<Map>> templates = projectConfig.getTemplates();
        List<Map> defaultTemplateDependency = AppUtil.getDefaultTemplateDependency(templates);
        SparkSubmitBean sparkSubmitBean = new SparkSubmitBean();
        List<String> usageArguments = new ArrayList<>();
        List<String> appArgumentList = new ArrayList<>();
        Map<String, String> othersConfMap = new LinkedHashMap<>();
        List<String> runScriptNotesList = projectInfoBean.getRunScriptNotesList();
        boolean isJavaBeanClass = true, isScalaBeanClass = true;
        List<Map> othersTemplatesDependency = "default".equals(templateName) ? null : templates.get(templateName + "Template");
        String templateImg = "";
        String aboutTemplate = null;
        switch (templateType) {
            case HBASE:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/nosql/hbase/hbase_logo.png?raw=true";
                template = new HBaseTemplate(className);
                break;
            case HIVE:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/warehouse/hive/hive_logo.jpg?raw=true";
                template = new HiveTemplate(className);
                break;
            case HWC:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/warehouse/hive/hive_logo.jpg?raw=true";
                runScriptNotesList.add("Update `hiveserver2_host` in `spark.sql.hive.hiveserver2.jdbc.url`");
                runScriptNotesList.add("Update `metastore_uri` in `spark.hadoop.hive.metastore.uris`");

                usageArguments.add("HIVE_SERVER2_JDBC_URL");
                usageArguments.add("HIVE_METASTORE_URI");

                if (projectInfoBean.isSecureCluster() && projectInfoBean.isSSLCluster()) {
                    usageArguments.add("HIVE_SERVER2_AUTH_KERBEROS_PRINCIPAL");
                } else if (projectInfoBean.isSecureCluster()) {
                    usageArguments.add("HIVE_SERVER2_AUTH_KERBEROS_PRINCIPAL");
                } else if (projectInfoBean.isSSLCluster()) {

                }

                othersConfMap.put("spark.sql.hive.hwc.execution.mode", "spark");
                othersConfMap.put("spark.datasource.hive.warehouse.load.staging.dir", "/tmp");
                othersConfMap.put("spark.datasource.hive.warehouse.read.via.llap", "false");
                othersConfMap.put("spark.datasource.hive.warehouse.read.jdbc.mode", "cluster");
                othersConfMap.put("spark.datasource.hive.warehouse.read.mode", "DIRECT_READER_V1");
                othersConfMap.put("spark.kryo.registrator", "com.qubole.spark.hiveacid.util.HiveAcidKyroRegistrator");
                othersConfMap.put("spark.sql.extensions", "com.hortonworks.spark.sql.rule.Extensions");
                othersConfMap.put("spark.sql.hive.hiveserver2.jdbc.url", "${HIVE_SERVER2_JDBC_URL}");
                othersConfMap.put("spark.hadoop.hive.metastore.uris", "thrift://${HIVE_METASTORE_URI}:9083");

                if (projectInfoBean.isSecureCluster()) {
                    runScriptNotesList.add("Update `hive.server2.authentication.kerberos.principal` in `spark.sql.hive.hiveserver2.jdbc.url.principal`");
                    othersConfMap.put("spark.security.credentials.hiveserver2.enabled", "false");
                    othersConfMap.put("spark.sql.hive.hiveserver2.jdbc.url.principal", "${HIVE_SERVER2_AUTH_KERBEROS_PRINCIPAL}");
                } else {
                    othersConfMap.put("spark.security.credentials.hiveserver2.enabled", "false");
                    othersConfMap.put("spark.datasource.hive.warehouse.user.name", "hive");
                    othersConfMap.put("spark.datasource.hive.warehouse.password", "hive");
                }

                template = new HWCTemplate(className);
                javaTemplate = new HWCJavaTemplate(javaClassName);
                break;
            case ORC:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/file_formats/orc_logo.png?raw=true";
                template = new OrcFileFormatTemplate(className);
                javaTemplate = new OrcFileFormatsJavaTemplate(javaClassName);
                break;
            case PARQUET:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/file_formats/parquet_logo.png?raw=true";
                template = new ParquetFileFormatTemplate(className);
                javaTemplate = new ParquetFileFormatJavaTemplate(javaClassName);
                break;
            case AVRO:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/file_formats/avro_logo.png?raw=true";
                template = new AvroFileFormatTemplate(className);
                javaTemplate = new AvroFileFormatJavaTemplate(javaClassName);
                break;
            case KAFKA:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/frameworks/kafka/kafka_logo.png?raw=true";
                isJavaBeanClass = isScalaBeanClass = false;
                String jaasFilePath = projectInfoBean.getJarDeployPath() + "/kafka_client_jaas.conf";
                othersConfMap.put("spark.driver.extraJavaOptions", "\"-Djava.security.auth.login.config=" + jaasFilePath + "\"");
                othersConfMap.put("spark.executor.extraJavaOptions", "\"-Djava.security.auth.login.config=" + jaasFilePath + "\"");

                List<String> fileList = new ArrayList<>();
                fileList.add(jaasFilePath);
                List<String> kafkaList = Arrays.asList("KAFKA_BOOTSTRAP_SERVERS", "KAFKA_TOPIC_NAMES");
                appArgumentList.addAll(kafkaList);
                usageArguments.addAll(kafkaList);

                if (projectInfoBean.isSSLCluster()) {
                    List<String> sslTruststoreList = Arrays.asList("SSL_TRUSTSTORE_LOCATION", "SSL_TRUSTSTORE_PASSWORD");
                    usageArguments.addAll(sslTruststoreList);
                    appArgumentList.addAll(sslTruststoreList);
                    fileList.add("${SSL_TRUSTSTORE_LOCATION}");
                }
                sparkSubmitBean.setFileList(fileList);
                template = new KafkaTemplate(projectInfoBean);
                break;
            case PHOENIX:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/nosql/phoenix/phoenix_logo.png?raw=true";
                isJavaBeanClass = isScalaBeanClass = false;
                template = new PhoenixTemplate(className);
                List<String> phoenixUsageList = Arrays.asList("PHOENIX_SERVER_URL", "TABLE_NAME");
                usageArguments.addAll(phoenixUsageList);
                appArgumentList.addAll(phoenixUsageList);
                break;
            case KUDU:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/nosql/kudu/kudu_logo.png?raw=true";
                template = new KuduTemplate(className);
                List<String> kuduUsageList = Arrays.asList("KUDU_MASTER");
                usageArguments.addAll(kuduUsageList);
                appArgumentList.addAll(kuduUsageList);
                break;
            case S3:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/cloud/aws/amazon_s3_logo.png?raw=true";
                template = new S3Template(className);
                List<String> s3UsageList = Arrays.asList("AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY", "BUCKET_NAME");
                usageArguments.addAll(s3UsageList);
                appArgumentList.addAll(s3UsageList);
                break;
            case GCS:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/cloud/gcp/gcs_logo.png?raw=true";
                template = new GcsTemplate(className);
                List<String> gcsUsageList = Arrays.asList("PROJECT_ID", "BUCKET_NAME", "PRIVATE_KEY", "PRIVATE_KEY_ID", "CLIENT_EMAIL");
                usageArguments.addAll(gcsUsageList);
                appArgumentList.addAll(gcsUsageList);
                break;
            case CASSANDRA:
                templateImg = "https://github.com/rangareddy/ranga-logos/blob/main/dbs/nosql/cassandra/cassandra_logo.png?raw=true";
                template = new CassandraTemplate(className);
                List<String> cassandraUsageList = Arrays.asList("CASSANDRA_HOST");
                usageArguments.addAll(cassandraUsageList);
                appArgumentList.addAll(cassandraUsageList);
                break;
            case DELTA:
                String deltaVersion = projectConfigMap.get("scalaBinaryVersion") + ":" +projectConfigMap.get("deltaVersion");
                templateImg = "https://docs.delta.io/latest/_static/delta-lake-logo.png";
                template = new DeltaTableTemplate(className, deltaVersion);
                aboutTemplate = "**Delta Lake** is a storage layer that brings scalable, ACID transactions to [Apache Spark](https://spark.apache.org/) and other big-data engines.";
                break;
            default:
                isJavaBeanClass = isScalaBeanClass = true;
                template = new DefaultTemplate(className);
                javaTemplate = new DefaultJavaTemplate(javaClassName);
        }

        projectInfoBean.setIntegrationImg(AppUtil.getIntegrationImage(templateImg));

        sparkSubmitBean.setOtherConfMap(othersConfMap);
        sparkSubmitBean.setUsageArgumentList(usageArguments);
        sparkSubmitBean.setAppArgumentList(appArgumentList);

        projectInfoBean.setCreateJavaBeanClass(isJavaBeanClass);
        projectInfoBean.setCreateScalaBeanClass(isScalaBeanClass);
        projectInfoBean.setRunScriptNotesList(runScriptNotesList);
        CodeTemplateBean codeTemplateBean = TemplateBuilder.getCodeTemplateBean(template);
        projectInfoBean.setScalaCodeTemplate(codeTemplateBean);
        projectInfoBean.setAboutTemplate(aboutTemplate);
        String setupInstructions = "";
        if(StringUtils.isNotEmpty(template.setupInstructions())) {
            setupInstructions = template.setupInstructions();
        }
        projectInfoBean.setSetUpInstructions(setupInstructions);

        if (javaTemplate != null) {
            projectInfoBean.setJavaTemplate(true);
            codeTemplateBean = TemplateBuilder.getCodeTemplateBean(javaTemplate);
            projectInfoBean.setJavaCodeTemplate(codeTemplateBean);
        }

        Set<Map> dependencyBeanSet = new LinkedHashSet<>(defaultTemplateDependency);
        if (CollectionUtils.isNotEmpty(othersTemplatesDependency)) {
            dependencyBeanSet.addAll(othersTemplatesDependency);
        }
        AppUtil.buildDependencies(projectConfig, dependencyBeanSet, projectInfoBean, projectConfigMap);
        SparkSubmitBuilder.buildSparkSubmit(sparkSubmitBean, projectInfoBean);
    }
}