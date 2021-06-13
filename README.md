# Spark Project Template Generator
Used to generate Sample Spark Project Template

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

#### Add your project names with comma seperated. For example,
```sh
appName=SparkCassandra,SparkHbase
```

### According to your cluster update the Java Version, Spark version, Scala versions. For example,
```sh
javaVersion=1.8
scalaVersion=2.12.11
scalaBinaryVersion=2.12
sparkVersion=3.0.1
sparkScope=compile
```

### Build the `spark_project_template_generator` project.
```sh
mvn clean package
```

### Run the `spark_project_template_generator` project to create Spark Project Templates.
```sh
java -jar target/spark-project-template-generator-1.0.0-SNAPSHOT.jar 
```