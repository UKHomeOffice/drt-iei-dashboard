import Dependencies.Libraries

lazy val root = (project in file("."))
  .settings(
    organization := "uk.gov.homeoffice",
    name := "drt-iei-dashboard",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeParser,
      Libraries.circeRefined,
      Libraries.cirisCore,
      Libraries.cirisEnum,
      Libraries.cirisRefined,
      Libraries.http4sDsl,
      Libraries.http4sServer,
      Libraries.http4sClient,
      Libraries.http4sCirce,
      Libraries.logback % Runtime,
      Libraries.logstashLogbackEncoder % Runtime,
      Libraries.skunkCore,
      Libraries.skunkCirce,
      Libraries.fs2CronCore,
      Libraries.scalaCheck,
      Libraries.scalaTest,
      Libraries.scalaTestPlus,
    ),
    dockerExposedPorts ++= Seq(9001),
    compile := ((compile in Compile) dependsOn buildReactApp).value
  ).enablePlugins(DockerPlugin).enablePlugins(JavaAppPackaging)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings",
)


val cwd = System.getProperty("user.dir")
val reactAppDir: sbt.File = new File(cwd + "/ui")

lazy val yarnInstall = taskKey[Unit]("yarn install")

yarnInstall := {
  scala.sys.process.Process("yarn install", reactAppDir).!
}

lazy val buildReactApp = taskKey[Unit]("Build react app")
buildReactApp := {
  scala.sys.process.Process("yarn build", reactAppDir).!
  scala.sys.process.Process(s"rm -rf $cwd/src/main/resources/ui").!
  scala.sys.process.Process(s"mv build $cwd/src/main/resources/ui", new File(cwd + "/ui")).!
}
