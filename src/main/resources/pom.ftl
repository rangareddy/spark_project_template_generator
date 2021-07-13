<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${projectBuilder.packageName}</groupId>
    <artifactId>${projectBuilder.projectName}</artifactId>
    <version>${projectBuilder.jarVersion}</version>
    <name>${projectBuilder.name}</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.build.targetEncoding>UTF-8</project.build.targetEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

        <maven.compiler.plugin.version>3.8.1</maven.compiler.plugin.version>
        <maven-shade-plugin.version>3.2.3</maven-shade-plugin.version>
        <!--<scala-maven-plugin.version>4.3.1</scala-maven-plugin.version>-->
        <scala-maven-plugin.version>3.2.2</scala-maven-plugin.version>
        <scalatest-maven-plugin.version>2.0.0</scalatest-maven-plugin.version>
        ${projectBuilder.mavenBuildToolBean.propertyVersions}
        <scalatest.version>3.0.8</scalatest.version>
        <junit.version>4.13.1</junit.version>
    </properties>

    <!-- Developers -->
    <developers>
        <developer>
            <id>${projectBuilder.authorId}</id>
            <name>${projectBuilder.author}</name>
            <url>https://github.com/${projectBuilder.authorId}</url>
        </developer>
    </developers>

    <!-- Repositories -->
    <repositories>
        ${projectBuilder.repoName}
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
        ${projectBuilder.mavenBuildToolBean.dependencies}
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
                    <filereports>${projectBuilder.projectName}TestSuites.txt</filereports>
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