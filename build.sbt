name := "scraperScala"

version := "0.1"

scalaVersion := "2.13.1"

resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"

libraryDependencies ++= Seq(
  "org.json4s" % "json4s-jackson" % "3.6.7",
  "com.typesafe" % "config" % "1.4.0",
  "net.ruippeixotog" % "scala-scraper" % "2.2.0"
)
  
