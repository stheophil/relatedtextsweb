import play.Project._

lazy val relatedtexts = project.in(file("modules/relatedtexts"))

lazy val relatedtextsweb = project.in(file("."))
    .aggregate(relatedtexts)
    .dependsOn(relatedtexts)

name := "relatedtextsweb"

version := "1.0"

libraryDependencies ++= Seq(
    filters
)

playScalaSettings
