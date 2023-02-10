name := "hello-world-sbt"
organization := "com.ranga"
organizationName := "Ranga"
description   := "Spark Tutorial"
version := "version"

developers := List(
    Developer(
        id    = "rangareddy",
        name  = "Ranga Reddy",
        email = "rangareddy.avula@gmail.com",
        url   = url("https://github.com/rangareddy")
    )
)

scalaVersion := "2.11.12"

lazy val sparkVersion = "2.4.0.7.1.6.0-297"
lazy val scalaTestVersion = "3.0.8"
lazy val javaVersion = "1.8"

publishMavenStyle := true

idePackagePrefix := Some("com.ranga.spark.hello.world")

// Java and JavaC options
javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion, "-Xlint")
javaOptions ++= Seq("-Xms6G", "-Xmx6G", "-XX:MaxPermSize=4048M", "-XX:+CMSClassUnloadingEnabled")

/*
resolvers += Resolver.mavenLocal

resolvers ++= Seq(
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)
*/

// Spark Dependencies
lazy val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
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

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
    case m if m.startsWith("META-INF") => MergeStrategy.discard
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", xs @ _*) => MergeStrategy.first
    case PathList("org", "jboss", xs @ _*) => MergeStrategy.first
    case "about.html"  => MergeStrategy.rename
    case "reference.conf" => MergeStrategy.concat
    case _ => MergeStrategy.first
  }
}