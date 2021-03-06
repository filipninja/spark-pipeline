name := "spark-pipeline"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0"
libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0" % "test"