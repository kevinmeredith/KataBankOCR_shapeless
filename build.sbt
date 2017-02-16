scalaVersion := "2.12.1"

scalacOptions := Seq("-Yinduction-heuristics")


libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2"
)

scalaOrganization in ThisBuild := "org.typelevel"
