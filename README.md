# Spark Project Template Generator

Used to generate multiple spark project templates using configuration file. 

## Advantages

The advantages of this application are:

1. Create **sbt** or **maven** or both build based Spark applications.
2. Through single configuration (`config.yaml` or `config_all_apps.yaml`) we can create **N number of Spark Applications**.
3. Supports various Spark templates like hive, hbase, kudu, various file formats etc.
4. Generate both **Scala** and **Java** based code. 
5. Generate the **run script** to run the spark application.
6. Deployment steps are mentioned in **README.md** file.
7. Built in **Scala Test** code.

## Supported Templates

The following spark templates are supported:

1. DEFAULT
1. HBASE
1. HIVE
1. KAFKA
1. PHOENIX
1. KUDU
1. HWC
1. ORC
1. AVRO
1. PARQUET
1. S3
1. GCS
1. CASSANDRA

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

Open the `config_all_apps.yaml` file and Update the configuration according to your cluster like Java Version, Spark version, Scala versions.

```sh
vi src/main/resources/config_all_apps.yaml
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

## Contribution

Send pull requests to keep this project updated.
