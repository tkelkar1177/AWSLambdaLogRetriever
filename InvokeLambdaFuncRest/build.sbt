name := "InvokeLambdaFuncRest"

version := "0.1"

scalaVersion := "2.13.7"

val logbackClassic = "1.2.3"
val scalaLogging = "3.9.4"
val Gson = "2.8.9"
val httpClient = "4.5.13"
val Config = "1.4.1"
val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion,
  "ch.qos.logback" % "logback-classic" % logbackClassic,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
  "com.google.code.gson" % "gson" % Gson,
  "org.apache.httpcomponents" % "httpclient" % httpClient,
  "com.typesafe" % "config" % Config
)