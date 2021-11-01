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

publishMavenStyle := true
autoScalaLibrary := false
${projectBuilder.sbtBuildToolBean.propertyVersions}
val testScope = "test"

resolvers ++= Seq(
${projectBuilder.sbtRepoName}
)

// Spark + Other Dependencies
lazy val appDependencies = Seq(
${projectBuilder.sbtBuildToolBean.dependencies}
)

// Test Dependencies
lazy val testDependencies = Seq(
 + "-" + module.revision + "." + artifact.extension
}

publish    "org.scalatest" %% "scalatest" % scalaTestVersion  % testScope,
    "junit" % "junit" % junitTestVersion % testScope
)

libraryDependencies ++= appDependencies ++ testDependencies

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    artifact.nameTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))