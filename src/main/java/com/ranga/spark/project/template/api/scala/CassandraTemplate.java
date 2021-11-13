package com.ranga.spark.project.template.api.scala;

public class CassandraTemplate extends ScalaBaseTemplate {

    public CassandraTemplate(String className) {
        super(className);
    }

    @Override
    public String sparkSessionBuildTemplate() {
        return "\n" +
                "        // Creating the SparkConf object\n" +
                "        val sparkConf = new SparkConf().setAppName(appName).setIfMissing(\"spark.master\", \"local[2]\")\n" +
                "\n" +
                "        val cassandraHost = args(0)\n" +
                "        val cassandraPort = if(args.length > 1) args(1) else \"9042\"\n" +
                "\n" +
                "        sparkConf.set(\"spark.cassandra.connection.host\", cassandraHost)\n" +
                "        sparkConf.set(\"spark.cassandra.connection.port\", cassandraPort)\n" +
                "\n" +
                "        // Creating the SparkSession object\n" +
                "        val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()\n" +
                "        println(\"SparkSession created successfully\")";
    }

    @Override
    public String setupInstructions() {
        return "## Cassandra schema\n" +
                "\n" +
                "### Launch `cqlsh` shell\n\n" +
                "```sh\n" +
                "cqlsh\n" +
                "```\n" +
                "\n" +
                "### Create a keyspace\n\n" +
                "```sql\n" +
                "cqlsh> CREATE KEYSPACE IF NOT EXISTS ranga_keyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };\n" +
                "cqlsh> use ranga_keyspace;\n" +
                "```\n" +
                "\n" +
                "### Create a table\n\n" +
                "```sql\n" +
                "cqlsh:ranga_keyspace> CREATE TABLE IF NOT EXISTS ranga_keyspace.employees(id bigint PRIMARY KEY, name TEXT, age int, salary float);\n" +
                "```\n" +
                "\n" +
                "### Insert the data\n\n" +
                "```sql\n" +
                "cqlsh:ranga_keyspace> INSERT INTO ranga_keyspace.employees(id, name, age, salary) VALUES (1, 'Ranga Reddy', 33, 50000.00);\n" +
                "cqlsh:ranga_keyspace> INSERT INTO ranga_keyspace.employees(id, name, age, salary) VALUES (2, 'Nishanth Reddy', 4, 80000.00);\n" +
                "cqlsh:ranga_keyspace> INSERT INTO ranga_keyspace.employees(id, name, age, salary) VALUES (3, 'Raja Sekhar Reddy', 60, 150000.00);\n" +
                "cqlsh:ranga_keyspace> INSERT INTO ranga_keyspace.employees(id, name, age, salary) VALUES (4, 'Mani Reddy', 16, 90000.00);\n" +
                "```\n" +
                "\n" +
                "### Select the data\n\n" +
                "```sql\n" +
                "cqlsh:ranga_keyspace> SELECT * FROM ranga_keyspace.employees;\n" +
                "\n" +
                " id | age | name              | salary\n" +
                "----+-----+-------------------+---------\n" +
                "  2 |   4 |    Nishanth Reddy |   80000\n" +
                "  3 |  60 | Raja Sekhar Reddy | 1.5e+05\n" +
                "  4 |  16 |        Mani Reddy |   90000\n" +
                "  1 |  33 |       Ranga Reddy |   50000\n" +
                "\n" +
                "(4 rows)\n" +
                "```";
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.{Dataset, Row, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String codeTemplate() {
        return "\n" +
                "        val tableName = \"employees\"\n" +
                "        val keyspace = \"ranga_keyspace\"\n" +
                "        val cassandraFormat = \"org.apache.spark.sql.cassandra\"\n" +
                "        val options = Map( \"keyspace\" -> keyspace, \"table\" -> tableName)\n" +
                "\n" +
                "        val employeeDF = spark.read.format(cassandraFormat).options(options).load()\n" +
                "        display(employeeDF)\n\n" +
                "        \n" +
                "        employeeDF.printSchema()\n" +
                "        employeeDF.show(truncate=false)  \n" +
                "\n" +
                "        import spark.implicits._\n" +
                "        var employeeDS = Seq(\n" +
                "          Employee(5L, \"Yashwanth\", 32, 80000.5f),\n" +
                "          Employee(6L, \"Vasundra Reddy\", 57, 180000.5f)\n" +
                "        ).toDS()\n" +
                "\n" +
                "        employeeDS.write.mode(org.apache.spark.sql.SaveMode.Append).format(cassandraFormat).options(options).save()\n" +
                "\n" +
                "        val empDF = spark.read.format(cassandraFormat).options(options).load()\n" +
                "        display(empDF)";
    }

    @Override
    public String methodsTemplate() {
        return "\n" +
                "    def display(df: Dataset[Row]) = {\n" +
                "      df.printSchema()\n" +
                "      df.show(truncate=false)\n" +
                "    }";
    }
}
