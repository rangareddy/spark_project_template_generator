# Spark Project Template Generator

Used to generate multiple spark project templates using configuration file. 

## Advantages

The advantages of this application are:

1. Create **sbt** or **maven** or both build based Spark applications.
2. Through Single Configuration we can create **N number of Spark Applications**.
3. Generate both **Scala** and **Java** based code. 
4. Generate the **run script** to run the spark application.
5. Deployment steps are mentioned in **README.md** file.
6. Built in **Scala Test** code.

## How to Use

### Download `spark_project_template_generator` project.

```sh
git clone https://github.com/rangareddy/spark_project_template_generator.git
cd spark_project_template_generator
```

### Update the Spark application details in `application.properties` file to create Spark project(s)

#### Open the `application.properties` file

```sh
vi src/main/resources/application.properties
```

#### Add your project names with comma separated. For example,

```sh
appName=SparkCassandra,SparkHbase
```

### Update target path where project needs to be created. For example,

```
targetDir=/Users/rangareddy.avula/project_templates
```

### According to your cluster update the Java Version, Spark version, Scala versions. For example,

```sh
javaVersion=1.8
scalaVersion=2.12.11
sparkVersion=3.0.1
sparkScope=compile
```

### Build the `spark_project_template_generator` project.

```sh
$ mvn clean package
```

### Run the `spark_project_template_generator` project to create Spark Project Templates.

```sh
$ java -jar target/spark-project-template-generator-1.0.0-SNAPSHOT.jar
```

### It will print the following output

```sh
========================
/Users/rangareddy.avula/project_templates/spark-cassandra-integration created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/README.md created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/run_spark_cassandra_integration_app.sh created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/pom.xml created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/src/main/scala/com/ranga/spark/cassandra/SparkCassandraIntegrationApp.scala created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/src/main/java/com/ranga/spark/cassandra/SparkCassandraIntegrationJavaApp.java created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/src/main/resources/log4j.properties created successfully
/Users/rangareddy.avula/project_templates/spark-cassandra-integration/.gitignore created successfully
========================

========================
/Users/rangareddy.avula/project_templates/spark-hbase-integration created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/README.md created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/run_spark_hbase_integration_app.sh created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/pom.xml created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/src/main/scala/com/ranga/spark/hbase/SparkHbaseIntegrationApp.scala created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/src/main/java/com/ranga/spark/hbase/SparkHbaseIntegrationJavaApp.java created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/src/main/resources/log4j.properties created successfully
/Users/rangareddy.avula/project_templates/spark-hbase-integration/.gitignore created successfully
========================
```

## Contribution

Send pull requests to keep this project updated.
