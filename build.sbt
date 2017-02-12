name := "bia-engine"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % "2.9.3" withSources() withJavadoc()
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.22"

javaOptions in run += "-Djava.library.path=libs/"
