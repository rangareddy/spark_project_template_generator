yum install -y git
yum install -y maven

git clone https://github.com/rangareddy/spark_project_template_generator.git $HOME/spark_project_template_generator
cd $HOME/spark_project_template_generator
mvn clean package
