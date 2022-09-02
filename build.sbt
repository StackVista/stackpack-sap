import Dependencies._

val stackpackName = "sap"

/*
 * General build setup
 *
 * NOTE: publishing the SAP StackPack is done by the StackState CI/CD pipeline. Its configuration is included in this build setup
 *       but requires StackState artifactory credentials which are not available to StackPack contributors.
 */

val host = "artifactory.tooling.stackstate.io"

val realm  = "Stackstate Artifactory"
val fileCredentials = Credentials(Path.userHome / ".sbt" / "stackstate-artifactory.credentials")

val publishRealm = "Artifactory Realm"
val publishFileCredentials = Credentials(Path.userHome / ".sbt" / "stackstate-artifactory-publish.credentials")

def getCredentials(realm: String, fallback: Credentials) =
  (
    for {
      user <- sys.env.get("ARTIFACTORY_USERNAME")
      pwd <- sys.env.get("ARTIFACTORY_PASSWORD")
    } yield Credentials(realm, host, user, pwd)
  ).getOrElse(fallback)

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.stackstate.stackpack",
      updateOptions := updateOptions.value.withGigahorse(false), // Work around for bug: https://github.com/sbt/sbt/issues/3570
      scalaVersion := "2.12.11",
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
        resolvers ++= Vector("Stackstate Artifactory" at "https://artifactory.tooling.stackstate.io/artifactory/libs-release", Resolver.mavenLocal),
        coursierUseSbtCredentials := true,
        // Publishing the SAP StackPack is done by the StackState CI/CD pipeline.
        credentials += getCredentials(realm, fileCredentials),
        publishTo := Some("Artifactory Realm" at "https://artifactory.tooling.stackstate.io/artifactory/libs"),
        // disable publishing the main doc jar
        publishArtifact in (Compile, packageDoc) := false,
        // disable publishing the main sources jar
        publishArtifact in (Compile, packageSrc) := false
    )),
    name := stackpackName
    // disable publishing the main project
    // publish / skip := true
  )

lazy val showVersion = taskKey[Unit]("Show version")

showVersion := {
  println(version.value)
}
