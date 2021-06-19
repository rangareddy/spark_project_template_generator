<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${projectBuilder.packageName}</groupId>
    <artifactId>${projectBuilder.projectName}</artifactId>
    <version>${projectBuilder.jarVersion}</version>
    <name>${projectBuilder.appName} Application</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.build.targetEncoding>UTF-8</project.build.targetEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

        <java.version>${projectBuilder.properties.javaVersion}</java.version>

        <maven.compiler.plugin.version>3.8.1</maven.compiler.plugin.version>
        <maven-shade-plugin.version>3.2.3</maven-shade-plugin.version>
        <scala-maven-plugin.version>4.3.1</scala-maven-plugin.version>
        <scalatest-maven-plugin.version>2.0.0</scalatest-maven-plugin.version>

        <scala.version>${projectBuilder.properties.scalaVersion}</scala.version>
        <scala.binary.version>${projectBuilder.properties.scalaBinaryVersion}</scala.binary.version>

        <spark.version>${projectBuilder.properties.sparkVersion}</spark.version>
        <spark.scope>${projectBuilder.properties.sparkScope}</spark.scope>

        <hbase.version>${projectBuilder.properties.hbaseVersion}</hbase.version>
        <hbase.scope>${projectBuilder.properties.sparkScope}</hbase.scope>

        <hbase.spark.version>${projectBuilder.properties.hbaseSparkVersion}</hbase.spark.version>
        <hbase.spark.scope>${projectBuilder.properties.sparkScope}</hbase.spark.scope>

        <hwc.version>${projectBuilder.properties.hbaseSparkVersion}</hwc.version>
        <hwc.scope>${projectBuilder.properties.sparkScope}</hwc.scope>

        <scalatest.version>3.0.8</scalatest.version>
        <junit.version>4.13.1</junit.version>
    </properties>

    <!-- Developers -->
    <developers>
        <developer>
            <id>rangareddy</id>
            <name>Ranga Reddy</name>
            <url>https://github.com/rangareddy</url>
        </developer>
    </developers>

    <!-- Repositories -->
    <repositories>

        <repository>
            <id>central</id>
            <name>Maven Central</name>
            <url>https://repo1.maven.org/maven2</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>

        <repository>
            <id>cldr-repo</id>
            <name>Cloudera Public Repo</name>
            <url>http://repository.cloudera.com/artifactory/cloudera-repos/</url>
        </repository>

        <repository>
            <id>hdp-repo</id>
            <name>Hortonworks Public Repo</name>
            <url>http://repo.hortonworks.com/content/repositories/releases/</url>
        </repository>

    </repositories>

    <dependencies>

        <!-- Scala Lang dependencies -->
        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-library</artifactId>
            <version>${r"${scala.version}"}</version>
        </dependency>

        <dependency>
            <groupId>org.scala-lang</groupId>
            <artifactId>scala-compiler</artifactId>
            <version>${r"${scala.version}"}</version>
        </dependency>

        <!-- Spark dependencies -->
        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-core_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${spark.version}"}</version>
            <scope>${r"${spark.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-sql_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${spark.version}"}</version>
            <scope>${r"${spark.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-streaming_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${spark.version}"}</version>
            <scope>${r"${spark.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-hive_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${spark.version}"}</version>
            <scope>${r"${spark.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>com.hortonworks.hive</groupId>
            <artifactId>hive-warehouse-connector_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${hwc.version}"}</version>
            <scope>${r"${hwc.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.hbase.connectors.spark</groupId>
            <artifactId>hbase-spark</artifactId>
            <version>${r"${hbase.spark.version}"}</version>
            <scope>${r"${hbase.spark.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-client</artifactId>
            <version>${r"${hbase.version}"}</version>
            <scope>${r"${hbase.scope}"}</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.hbase</groupId>
            <artifactId>hbase-server</artifactId>
            <version>${r"${hbase.version}"}</version>
            <scope>${r"${hbase.scope}"}</scope>
        </dependency>

        <!-- Scala Test dependencies -->
        <dependency>
            <groupId>org.scalatest</groupId>
            <artifactId>scalatest_${r"${scala.binary.version}"}</artifactId>
            <version>${r"${scalatest.version}"}</version>
            <scope>test</scope>
        </dependency>

        <!-- Junit dependencies -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${r"${junit.version}"}</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

    <build>
        <plugins>

            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${r"${maven.compiler.plugin.version}"}</version>
                <configuration>
                    <source>${r"${java.version}"}</source>
                    <target>${r"${java.version}"}</target>
                    <compilerArgs>
                        <arg>-Xlint:all,-serial,-path</arg>
                    </compilerArgs>
                </configuration>
            </plugin>

            <!-- Scala Maven Plugin -->
            <plugin>
                <groupId>net.alchim31.maven</groupId>
                <artifactId>scala-maven-plugin</artifactId>
                <version>${r"${scala-maven-plugin.version}"}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <scalaVersion>${r"${scala.version}"}</scalaVersion>
                    <scalaCompatVersion>${r"${scala.binary.version}"}</scalaCompatVersion>
                    <checkMultipleScalaVersions>true</checkMultipleScalaVersions>
                    <failOnMultipleScalaVersions>true</failOnMultipleScalaVersions>
                    <recompileMode>incremental</recompileMode>
                    <charset>${r"${project.build.sourceEncoding}"}</charset>
                    <args>
                        <arg>-unchecked</arg>
                        <arg>-deprecation</arg>
                        <arg>-feature</arg>
                    </args>
                    <jvmArgs>
                      <jvmArg>-Xss64m</jvmArg>
                      <jvmArg>-Xms1024m</jvmArg>
                      <jvmArg>-Xmx1024m</jvmArg>
                      <jvmArg>-XX:ReservedCodeCacheSize=1g</jvmArg>
                    </jvmArgs>
                    <javacArgs>
                        <javacArg>-source</javacArg>
                        <javacArg>${r"${java.version}"}</javacArg>
                        <javacArg>-target</javacArg>
                        <javacArg>${r"${java.version}"}</javacArg>
                        <javacArg>-Xlint:all,-serial,-path</javacArg>
                    </javacArgs>
                </configuration>
            </plugin>

            <!-- ScalaTest Maven Plugin -->
            <plugin>
                <groupId>org.scalatest</groupId>
                <artifactId>scalatest-maven-plugin</artifactId>
                <version>${r"${scalatest-maven-plugin.version}"}</version>
                <configuration>
                    <reportsDirectory> ${r"${project.build.directory}"}/surefire-reports</reportsDirectory>
                    <junitxml>.</junitxml>
                    <filereports>${projectBuilder.appName}TestSuites.txt</filereports>
                </configuration>
                <executions>
                    <execution>
                        <id>test</id>
                        <goals>
                            <goal>test</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Maven Shade Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>${r"${maven-shade-plugin.version}"}</version>
                <executions>
                    <!-- Run shade goal on package phase -->
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>

                        <configuration>
                            <createDependencyReducedPom>true</createDependencyReducedPom>
                            <transformers>
                                <!-- Add Main Class to manifest file -->
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>${projectBuilder.fullClassName}</mainClass>
                                </transformer>
                            </transformers>

                            <filters>
                                <filter>
                                    <artifact>*:*</artifact>
                                    <excludes>
                                        <exclude>META-INF/*.SF</exclude>
                                        <exclude>META-INF/*.DSA</exclude>
                                        <exclude>META-INF/*.RSA</exclude>
                                    </excludes>
                                </filter>
                            </filters>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

        </plugins>
    </build>
</project>