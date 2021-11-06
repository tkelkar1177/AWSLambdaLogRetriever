name := "InvokeLambdaFuncRest"

version := "0.1"

scalaVersion := "2.13.7"

val logbackClassic = "1.2.3"
val scalaLogging = "3.9.4"
val gson = "2.8.9"
val httpClient = "4.5.13"
val Config = "1.4.1"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % logbackClassic,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
  "com.google.code.gson" % "gson" % gson,
  "org.apache.httpcomponents" % "httpclient" % httpClient,
  "com.typesafe" % "config" % Config
)