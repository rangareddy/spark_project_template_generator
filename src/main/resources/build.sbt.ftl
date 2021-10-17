name := "${projectBuilder.projectName}"
organization := "${projectBuilder.packageName}"
description   := "${projectBuilder.projectDescription}"
version := "${projectBuilder.jarVersion}"

developers := List(
    Developer(
        id    = "${projectBuilder.authorId}",
        name  = "${projectBuilder.author}",
        email = "${projectBuilder.authorEmail}",
        url   = url("https://github.com/${projectBuilder.authorId}")
    )
)

val scalaVersion = "2.11.12"
val sparkVersion = "2.4.0.7.1.6.0-297"
val sparkScope = "compile"
val scalaTestVersion = "3.0.8"
val javaVersion = "1.8"

publishMavenStyle := true

// Java and JavaC options
javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion, "-Xlint")
javaOptions ++= Seq("-Xms6G", "-Xmx6G", "-XX:MaxPermSize=4048M", "-XX:+CMSClassUnloadingEnabled")

/*
resolvers += Resolver.mavenLocal

resolvers ++= Seq(
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "apache-snapshots" at "http://repository.apache.org/snapshots/",
  "Maven2 repository" at "https://repo1.maven.org/maven2/",
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "cloudera-repo" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)
*/

// Spark Dependencies
lazy val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % sparkScope,
  "org.apache.spark" %% "spark-sql" % sparkVersion % sparkScope,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % sparkScope,
  "org.apache.spark" %% "spark-hive" % sparkVersion % sparkScope
)

// Other Dependencies
lazy val auxLib = Seq(

)

// Test Dependencies
lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion  % "test",
  "junit" % "junit" % "4.13.1" % "test"
)

libraryDependencies ++= sparkDependencies ++ testDependencies

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}