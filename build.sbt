name := "bia-engine"
version := "1.0"
scalaVersion := "2.11.1"

val lwjglVersion = "2.9.3"
val slick2dVersion = "1.0.2"
val slf4jVersion = "1.7.22"

// lwjgl
libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl" % lwjglVersion
libraryDependencies += "org.lwjgl.lwjgl" % "lwjgl_util" % lwjglVersion
// slick
libraryDependencies += "org.slick2d" % "slick2d-core" % slick2dVersion
// logging
libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion
libraryDependencies += "org.slf4j" % "slf4j-simple" % slf4jVersion
