logLevel := Level.Warn

addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")

// sbt eclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")

//
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.9")

// scala style
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// Scala options
scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-Xlint", "-feature", "-explain")