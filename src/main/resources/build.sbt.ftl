name := "${projectBuilder.projectName}"
organization := "${projectBuilder.organization}"
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

publishMavenStyle := true
autoScalaLibrary := false
${projectBuilder.sbtBuildToolBean.propertyVersions}
val testScope = "test"

resolvers ++= Seq(
    "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
    "Maven2 repository" at "https://repo1.maven.org/maven2/",
    "cloudera-repo" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)

// Spark + Other Dependencies
lazy val appDependencies = Seq(
${projectBuilder.sbtBuildToolBean.dependencies}
)

// Test Dependencies
lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion  % testScope,
    "junit" % "junit" % junitTestVersion % testScope
)

libraryDependencies ++= appDependencies ++ testDependencies

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    artifact.name + "-" + module.revision + "." + artifact.extension
}

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))