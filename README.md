# Spark Project Template Generator

Used to generate single or multiple spark projects using existing or customized templates by specifying configuration file(yaml). 

## Advantages

The advantages of this application are:

1. Create **sbt** or **maven** or both build based Spark applications.
2. Through single configuration (`config.yaml` or `config_all_apps.yaml`) we can create **N number of Spark Applications**.
3. Supports various Spark templates like hive, hbase, kudu, various file formats etc.
4. Generate both **Scala** and **Java** based code. 
5. Generate the **run script** to run the spark application.
6. Deployment steps are mentioned in **README.md** file.
7. Built in **Scala Test** code.
8. If your cluster is enabled **kerberos** or **ssl** or **both**, according to your cluster it will generate appropriate type of applications. 

## Supported Templates

The following spark templates are supported:

| Template Name | Template Description                       | Scala Code | Java Code | Python Code | Test Code | Sample Code Link |
| ------------- | ------------------------------------------ | ---------- | --------- | ----------- | --------- | ---------------- |
| DEFAULT       | Spark Hello World Integration              | &check;    | &check;   | &#10539;    | &check;   |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-hello-world-integration)|
| HBASE         | Spark HBase Integration                    | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-hbase-integration)|
| HIVE          | Spark Hive Integration                     | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-hive-integration)|
| KAFKA         | Spark Kafka Integration                    | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-kafka-integration)|
| PHOENIX       | Spark Phoenix Integration                  | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-phoenix-integration)|
| KUDU          | Spark Phoenix Integration                  | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-kudu-integration)|
| HWC           | Spark Hive Warehouse Connector Integration | &check;    | &check;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-hwc-integration)|
| ORC           | Spark ORC File Format Integration          | &check;    | &check;  | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-orc-integration)|
| AVRO          | Spark Avro File Format Integration         | &check;    | &check;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-avro-integration)|
| PARQUET       | Spark Parquet File Format Integration      | &check;    | &check;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-parquet-integration)|
| S3            | Spark AWS S3 Storage Integration           | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-s3-integration)|
| GCS           | Spark Google Cloud Storage Integration     | &check;    | &check;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark_gcs_integration)|
| CASSANDRA     | Spark Cassandra Integration                | &check;    | &#10539;   | &#10539;    | &#10539;  |[Code](https://github.com/rangareddy/ranga_spark_experiments/tree/master/spark-cassandra-integration)|

## How to Use

### Download `spark_project_template_generator` project.

```sh
git clone https://github.com/rangareddy/spark_project_template_generator.git
cd spark_project_template_generator
```

### Update the Spark application details in `config.yaml` or `config_all_apps.yaml` file to create Spark project(s).

**Note:** 

* By `config.yaml` configuration file, we can create single project by default.
* By `config_all_apps.yaml` configuration file, we can create multiple project(s) by default.

#### Update the Configuration 

Open the configuration file and update the configuration according to your cluster like Java Version, Spark version, Scala versions.

**Single Project Template Configuration file**

```sh
vi src/main/resources/config.yaml
```

**Multiple Projects Template Configuration file**

```sh
vi src/main/resources/config_all_apps.yaml
```

|Property Name|Property Description|Default Value| 
|---|---|---|
|baseProjectDir|Base Project Template Directory| User Home Directory - System.getProperty("user.home") |
|basePackageName|Base Package Name for your project | com.ranga |
|baseDeployJarPath|Based Deploy Path to deploy your application in cluster |/apps/spark/ |
|buildTools| Supported Build tools: **maven, sbt** | maven|
|jarVersion|Jar Version for your project |1.0.0-SNAPSHOT |
|scalaVersion| Scala Version for your project |2.12.10|
|javaVersion| Java Version for your project| 1.8|
|sbtVersion| SBT Build tool Version for your project |0.13.17|
|scope| Spark jars global application scope|compile|
|secureCluster| If your cluster is enabled kerberized then you can use this parameter|false |
|sslCluster|If your cluster is enabled ssl then you can use this parameter |false|
|author| Specify the author name |Ranga Reddy |
|authorEmail|Specify the author email | |
|projectDetails|We can specify the project details like projectName, templateName, project description | |
|componentVersions| We can specify what is the component name, version and its scope. If scope is not specified then it will pick global scope| |
|templates|For each template what are all the jars files is required we need to specify here||

**Note:** Please update your configuration file properly otherwise you will get configuration issues.

### Build the `spark_project_template_generator` project.

```sh
$ mvn clean package
```

### Run the `spark_project_template_generator` project to create Spark Project Templates.

**Creating the Single project using config.yaml.**

```sh
$ java -jar target/spark-project-template-generator-1.0.0-SNAPSHOT.jar
```

**or**

```sh
$ java -jar target/spark-project-template-generator-1.0.0-SNAPSHOT.jar src/main/resources/config.yaml
```

**Creating the Multiple projects using src/main/resources/config_all_apps.yaml.**

```sh
$ java -jar target/spark-project-template-generator-1.0.0-SNAPSHOT.jar src/main/resources/config_all_apps.yaml
```

### It will print the following output

```sh
Application <spark-hello-world-integration> created successfully.
Application <spark-hive-integration> created successfully.
Application <spark-hbase-integration> created successfully.
Application <spark-hwc-integration> created successfully.
Application <spark-kafka-integration> created successfully.
Application <spark-phoenix-integration> created successfully.
Application <spark-kudu-integration> created successfully.
Application <spark-orc-integration> created successfully.
Application <spark-avro-integration> created successfully.
Application <spark-parquet-integration> created successfully.
Application <spark-cassandra-integration> created successfully.
Application <spark-s3-integration> created successfully.
Application <spark-gcs-integration> created successfully.
```

## Sample Applications

By using this application i have created most of the spark applications mentioned in the following github. 

`https://github.com/rangareddy/ranga_spark_experiments`

## Contribution

Send pull requests to keep this project updated.
