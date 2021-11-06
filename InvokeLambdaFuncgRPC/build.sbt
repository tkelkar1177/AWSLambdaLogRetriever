name := "InvokeLambdaFuncgRPC"

version := "0.1"

scalaVersion := "2.13.7"

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

val scalacticVersion = "3.2.9"
val Config = "1.4.1"
val logbackClassic = "1.2.3"
val scalaLogging = "3.9.4"
val gson = "2.8.9"
val httpClient = "4.5.13"

libraryDependencies ++= Seq(
  "com.thesamet.scalapb"  %% "scalapb-runtime"        % scalapb.compiler.Version.scalapbVersion   % "protobuf",
  "com.thesamet.scalapb"  %% "scalapb-runtime-grpc"   % scalapb.compiler.Version.scalapbVersion,
  "org.scalactic"         %% "scalactic"              % scalacticVersion,
  "org.scalatest"         %% "scalatest"              % scalacticVersion                           % Test,
  "org.scalatest"         %% "scalatest-featurespec"  % scalacticVersion                           % Test,
  "io.grpc"                % "grpc-netty"             % scalapb.compiler.Version.grpcJavaVersion,
  "com.typesafe" % "config" % Config,
  "ch.qos.logback" % "logback-classic" % logbackClassic,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
  "com.google.code.gson" % "gson" % gson,
  "org.apache.httpcomponents" % "httpclient" % httpClient,
  "com.typesafe" % "config" % Config
)