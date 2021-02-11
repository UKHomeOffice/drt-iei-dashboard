import sbt._

object Dependencies {

  object Versions {
    val cats = "2.2.0"
    val catsEffect = "2.2.0"
    val catsMeowMtl = "0.4.1"
    val catsRetry = "2.0.0"
    val circe = "0.13.0"
    val ciris = "1.2.1"
    val javaxCrypto = "1.0.1"
    val fs2 = "2.4.6"
    val http4s = "0.21.13"
    val http4sJwtAuth = "0.0.5"
    val log4cats = "1.1.1"
    val newtype = "0.4.3"
    val refined = "0.9.18"
    val redis4cats = "0.10.3"
    val skunk = "0.0.21"
    val squants = "1.7.0"

    val betterMonadicFor = "0.3.1"
    val kindProjector = "0.11.2"
    val logback = "1.2.3"

    val scalaCheck = "1.15.1"
    val scalaTest = "3.2.3"
    val scalaTestPlus = "3.2.2.0"
    val logstashLogbackEncoder = "6.5"
    val fs2CronCore = "0.2.2"
    val scalaTestMockito = "3.2.2.0"
    
  }

  object Libraries {
    
    def circe(artifact: String): ModuleID = "io.circe" %% artifact % Versions.circe

    def ciris(artifact: String): ModuleID = "is.cir" %% artifact % Versions.ciris

    def http4s(artifact: String): ModuleID = "org.http4s" %% artifact % Versions.http4s

    val cats = "org.typelevel" %% "cats-core" % Versions.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect
    val circeCore = circe("circe-core")
    val circeGeneric = circe("circe-generic")
    val circeParser = circe("circe-parser")
    val circeRefined = circe("circe-refined")
    val cirisCore    = ciris("ciris")
    val cirisEnum    = ciris("ciris-enumeratum")
    val cirisRefined = ciris("ciris-refined")
    val http4sDsl = http4s("http4s-dsl")
    val http4sServer = http4s("http4s-blaze-server")
    val http4sClient = http4s("http4s-blaze-client")
    val http4sCirce = http4s("http4s-circe")

    val skunkCore = "org.tpolecat" %% "skunk-core" % Versions.skunk
    val skunkCirce = "org.tpolecat" %% "skunk-circe" % Versions.skunk
    
    val fs2CronCore = "eu.timepit" %% "fs2-cron-core" % Versions.fs2CronCore

    // Runtime
    val logback = "ch.qos.logback" % "logback-classic" % Versions.logback
    val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % Versions.logstashLogbackEncoder
    // Test
    val scalaCheck = "org.scalacheck" %% "scalacheck" % Versions.scalaCheck
    val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest
    val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-14" % Versions.scalaTestPlus
    val scalaTestMockito = "org.scalatestplus" %% "mockito-3-4" % Versions.scalaTestMockito
    val log4cats = "io.chrisdavenport" %% "log4cats-slf4j" % Versions.log4cats
  }

}
