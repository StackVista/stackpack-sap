import Dependencies._

/*
 * StackPacks
 *
 * Also add each stackpack to 'allStackpacks'
 */
lazy val sap = project in file("sap")

lazy val allStackpacks = List(sap)

/*
 * General build setup, don't touch this when adding a StackPack
 */
lazy val root = (project in file("."))
  .disablePlugins(StackPack)
  .settings(
    inThisBuild(List(
      organization := "com.stackstate.stackpack",
      updateOptions := updateOptions.value.withGigahorse(false), // Work around for bug: https://github.com/sbt/sbt/issues/3570
      scalaVersion := "2.12.6",
        scalacOptions ++= Vector(
          "-target:jvm-1.8",
          "-encoding",
          "UTF-8",
          "-feature",
          "-language:implicitConversions",
          "-language:postfixOps",
          "-unchecked",
          "-deprecation",
          "-Xfatal-warnings",
          "-Xmax-classfile-name",
          "242",
          "-Yrangepos"
        ),
        javacOptions ++= Vector("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation", "-Werror"),
        resolvers ++= Vector("Stackstate Artifactory" at "https://artifactory.stackstate.io/artifactory/libs-release", Resolver.mavenLocal),
        coursierUseSbtCredentials := true,
        credentials += Credentials(Path.userHome / ".sbt" / "stackstate-artifactory.credentials"),
        credentials += Credentials(Path.userHome / ".sbt" / "stackstate-artifactory-publish.credentials"),
        publishTo := Some("Artifactory Realm" at "https://artifactory.stackstate.io/artifactory/libs"),
        // disable publishing the main doc jar
        publishArtifact in (Compile, packageDoc) := false,
        // disable publishing the main sources jar
        publishArtifact in (Compile, packageSrc) := false
    )),
    name := "stackpacks",
    // disable publishing the main project
    publish / skip := true
  ).aggregate(allStackpacks.map(Project.projectToRef):_*)
