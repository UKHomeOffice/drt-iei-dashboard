

val Http4sVersion = "0.21.8"
val CirceVersion = "0.13.0"
val Specs2Version = "4.10.5"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  //  .enablePlugins(SbtJsEngine)
  .settings(
  organization := "uk.gov.homeoffice",
  name := "drt-iei-dashboard",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.13.3",
  libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
    "org.http4s" %% "http4s-circe" % Http4sVersion,
    "org.http4s" %% "http4s-dsl" % Http4sVersion,
    "io.circe" %% "circe-generic" % CirceVersion,
    "org.specs2" %% "specs2-core" % Specs2Version % "test",
    "ch.qos.logback" % "logback-classic" % LogbackVersion,
    "org.scalameta" %% "svm-subs" % "20.2.0"
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
  compile := ((compile in Compile) dependsOn buildReactApp).value
)
//  .settings(JsEngineKeys.engineType := JsEngineKeys.EngineType.Node)

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
