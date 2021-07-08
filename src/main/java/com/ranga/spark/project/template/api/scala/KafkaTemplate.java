package com.ranga.spark.project.template.api.scala;

import com.ranga.spark.project.template.bean.ProjectInfoBean;
import org.apache.commons.lang3.StringUtils;

public class KafkaTemplate extends ScalaBaseTemplate {
    private ProjectInfoBean projectInfoBean;
    public KafkaTemplate(ProjectInfoBean projectInfoBean) {
        super(projectInfoBean.getClassName());
        this.projectInfoBean = projectInfoBean;
    }

    @Override
    public String setupInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        return sb.toString();
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.SparkSession\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String codeTemplate() {

        String securityProtocol = "";
        if(projectInfoBean.isSecureCluster() && projectInfoBean.isSSLCluster()) {
            securityProtocol = "SASL_SSL";
        } else if(projectInfoBean.isSecureCluster()) {
            securityProtocol = "SASL_PLAINTEXT";
        } else if(projectInfoBean.isSSLCluster()) {
            securityProtocol = "SSL";
        }

        StringBuilder sb = new StringBuilder();
            sb.append("val kafkaBootstrapServers = args(0)\n").
            append("        val inputTopicNames = args(1)\n");

            if(projectInfoBean.isSSLCluster()) {
                sb.append("        val sslTruststoreLocation = args(2)\n");
                sb.append("        val sslTruststorePassword = args(3)\n");
            }

            sb.append("\n" ).
            append("        val inputDf = spark.\n" ).
            append("            readStream.\n" ).
            append("            format(\"kafka\").\n" ).
            append("            option(\"kafka.bootstrap.servers\", kafkaBootstrapServers).\n" ).
            append("            option(\"subscribe\", inputTopicNames).\n" ).
            append("            option(\"startingOffsets\", \"earliest\"). \n" );

            if(StringUtils.isNotEmpty(securityProtocol)) {
                sb.append( "            option(\"kafka.security.protocol\",\""+securityProtocol+"\"). \n" );
            }

            if(projectInfoBean.isSSLCluster()) {
                sb.append( "            option(\"kafka.ssl.truststore.location\", sslTruststoreLocation).\n" );
                sb.append( "            option(\"kafka.ssl.truststore.password\", sslTruststorePassword).\n" );
            }

            sb.
            append("            load()\n" ).
            append("        \n" ).
            append("        inputDf.printSchema()\n" ).
            append("\n" ).
            append("        val outputDF = inputDf.writeStream.\n" ).
            append("            format(\"console\").\n" ).
            append("            outputMode(\"append\").\n" ).
            append("            option(\"truncate\", \"false\").\n" ).
            append("            start()\n" ).
            append("\n" ).
            append("        outputDF.awaitTermination()");
        return sb.toString();
    }

    @Override
    public String sparkSessionCloseTemplate() {
        return "";
    }
}