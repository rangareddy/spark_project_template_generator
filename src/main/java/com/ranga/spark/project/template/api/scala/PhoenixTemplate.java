package com.ranga.spark.project.template.api.scala;

public class PhoenixTemplate extends ScalaBaseTemplate {

    public PhoenixTemplate(String className) {
        super(className);
    }

    @Override
    public String setupInstructions() {

        StringBuilder sb = new StringBuilder();
        sb.append("## CDP Integration\n" +
                    "\n" +
                    "### Step1: Launch the Phoenix Shell using sqlline.py\n" +
                    "```sh\n" +
                    "python /opt/cloudera/parcels/CDH/lib/phoenix/bin/sqlline.py\n" +
                    "```\n" +
                    "\n" +
                    "### Step2: Create an EMPLOYEE table in Phoenix\n" +
                    "```sql\n" +
                    "> CREATE TABLE IF NOT EXISTS EMPLOYEE (\n" +
                    "  ID BIGINT NOT NULL, \n" +
                    "  NAME VARCHAR, \n" +
                    "  AGE INTEGER, \n" +
                    "  SALARY FLOAT\n" +
                    "  CONSTRAINT emp_pk PRIMARY KEY (ID)\n" +
                    ");\n" +
                    "```\n" +
                    "\n" +
                    "### Step3: List the Phoenix tables\n" +
                    "```sql\n" +
                    "> !tables\n" +
                    "+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+-----+\n" +
                    "| TABLE_CAT  | TABLE_SCHEM  | TABLE_NAME  |  TABLE_TYPE   | REMARKS  | TYPE_NAME  | SELF_REFERENCING_COL_NAME  | REF_GENERATION  | INDEX_STATE  | IMMUTABLE_ROWS  | SALT_BUCKETS  | MUL |\n" +
                    "+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+-----+\n" +
                    "|            | SYSTEM       | CATALOG     | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | fal |\n" +
                    "|            | SYSTEM       | FUNCTION    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | fal |\n" +
                    "|            | SYSTEM       | LOG         | SYSTEM TABLE  |          |            |                            |                 |              | true            | 32            | fal |\n" +
                    "|            | SYSTEM       | SEQUENCE    | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | fal |\n" +
                    "|            | SYSTEM       | STATS       | SYSTEM TABLE  |          |            |                            |                 |              | false           | null          | fal |\n" +
                    "|            |              | EMPLOYEE    | TABLE         |          |            |                            |                 |              | false           | null          | fal |\n" +
                    "+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+-----------------+---------------+-----+\n" +
                    "```\n" +
                    "\n" +
                    "### Step4: Insert the data to EMPLOYEE table in Phoenix\n" +
                    "```sql\n" +
                    "UPSERT INTO EMPLOYEE (ID, NAME, AGE, SALARY) VALUES (1, 'Ranga', 32, 10000);\n" +
                    "UPSERT INTO EMPLOYEE (ID, NAME, AGE, SALARY) VALUES (2, 'Nishanth', 2, 30000);\n" +
                    "UPSERT INTO EMPLOYEE (ID, NAME, AGE, SALARY) VALUES (3, 'Raja', 52, 60000);\n" +
                    "UPSERT INTO EMPLOYEE (ID, NAME, AGE, SALARY) VALUES (4, 'Yashu', 10, 8000);\n" +
                    "UPSERT INTO EMPLOYEE (ID, NAME, AGE, SALARY) VALUES (5, 'Manoj', 16, 15000);\n" +
                    "```\n" +
                    "\n" +
                    "### Step5: Select the Employee data\n" +
                    "```sql\n" +
                    "> SELECT * FROM EMPLOYEE;\n" +
                    "+-----+-----------+------+----------+\n" +
                    "| ID  |   NAME    | AGE  |  SALARY  |\n" +
                    "+-----+-----------+------+----------+\n" +
                    "| 1   | Ranga     | 32   | 10000.0  |\n" +
                    "| 2   | Nishanth  | 2    | 30000.0  |\n" +
                    "| 3   | Raja      | 52   | 60000.0  |\n" +
                    "| 4   | Yashu     | 10   | 8000.0   |\n" +
                    "| 5   | Manoj     | 16   | 15000.0  |\n" +
                    "+-----+-----------+------+----------+\n" +
                    "5 rows selected (0.07 seconds)\n" +
                    "\n" +
                    "> !quit\n" +
                    "```\n" +
                    "\n" +
                    "### Step6: Login to hbase shell and check the EMPLOYEE table data in HBase\n" +
                    "```sh\n" +
                    "hbase shell\n" +
                    "\n" +
                    "> scan 'EMPLOYEE'\n" +
                    "ROW                                             COLUMN+CELL\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x01               column=0:\\x00\\x00\\x00\\x00, timestamp=1615911856369, value=x\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x01               column=0:\\x80\\x0B, timestamp=1615911856369, value=Ranga\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x01               column=0:\\x80\\x0C, timestamp=1615911856369, value=\\x80\\x00\\x00\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x01               column=0:\\x80\\x0D, timestamp=1615911856369, value=\\xC6\\x1C@\\x01\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x02               column=0:\\x00\\x00\\x00\\x00, timestamp=1615911856418, value=x\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x02               column=0:\\x80\\x0B, timestamp=1615911856418, value=Nishanth\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x02               column=0:\\x80\\x0C, timestamp=1615911856418, value=\\x80\\x00\\x00\\x02\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x02               column=0:\\x80\\x0D, timestamp=1615911856418, value=\\xC6\\xEA`\\x01\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x03               column=0:\\x00\\x00\\x00\\x00, timestamp=1615911856448, value=x\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x03               column=0:\\x80\\x0B, timestamp=1615911856448, value=Raja\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x03               column=0:\\x80\\x0C, timestamp=1615911856448, value=\\x80\\x00\\x004\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x03               column=0:\\x80\\x0D, timestamp=1615911856448, value=\\xC7j`\\x01\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x04               column=0:\\x00\\x00\\x00\\x00, timestamp=1615911856478, value=x\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x04               column=0:\\x80\\x0B, timestamp=1615911856478, value=Yashu\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x04               column=0:\\x80\\x0C, timestamp=1615911856478, value=\\x80\\x00\\x00\\x0A\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x04               column=0:\\x80\\x0D, timestamp=1615911856478, value=\\xC5\\xFA\\x00\\x01\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x05               column=0:\\x00\\x00\\x00\\x00, timestamp=1615911856507, value=x\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x05               column=0:\\x80\\x0B, timestamp=1615911856507, value=Manoj\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x05               column=0:\\x80\\x0C, timestamp=1615911856507, value=\\x80\\x00\\x00\\x10\n" +
                    " \\x80\\x00\\x00\\x00\\x00\\x00\\x00\\x05               column=0:\\x80\\x0D, timestamp=1615911856507, value=\\xC6j`\\x01\n" +
                    " \n" +
                    " > exit\n" +
                    "```\n"

        );

        return sb.toString();
    }

    @Override
    public String importTemplate() {
        return "import org.apache.spark.sql.{SQLContext, SparkSession}\n" +
                "import org.apache.spark.SparkConf\n" +
                "import org.apache.log4j.Logger";
    }

    @Override
    public String codeTemplate() {
        return  "val sqlContext = spark.sqlContext\n" +
                "\n" +
                "        var phoenixServerUrl = args(0) // val zkUrl=\"phoenix-server:2181\"\n" +
                "        val tableName = args(1)\n" +
                "        \n" +
                "        if(!phoenixServerUrl.contains(\":\")) {\n" +
                "            phoenixServerUrl = phoenixServerUrl +\":2181\"\n" +
                "        }\n\n"+
                "        val inputDF = sqlContext.load(\n" +
                "          \"org.apache.phoenix.spark\",\n" +
                "          Map(\"table\" -> tableName, \"zkUrl\" -> phoenixServerUrl)\n" +
                "        )\n" +
                "      \n" +
                "        inputDF.show(true)";
    }
}