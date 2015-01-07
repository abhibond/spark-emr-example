//import AssemblyKeys._
// Required when using sbt-assembly

organization := "com.abhibon"

name         := "spark-emr-example"

version      := "0.1"

scalaVersion := "2.10.4"

scalacOptions := Seq("-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Spray Repository" at "http://repo.spray.cc/"
)

// As of 2015-01-07, Hadoop 2.4.0 is the latest supported on AWS EMR
// As of 2015-01-07, Spark 1.2.0 is the latest supported on AWS EMR
libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.4.0" % "provided",
  "org.apache.spark" % "spark-core_2.10" % "1.2.0" % "provided",
  "com.google.guava" % "guava" % "11.0.1" % "test",
  "org.specs2" % "specs2_2.10" % "2.2" % "test"
)

// Required for sbt-assembly
//assemblySettings

assemblyJarName in assembly := "spark-emr-example.jar"

//mainClass in assembly := Some("com.sharethrough.emr_tutorial.JobRunner")

// Prevent tests from running into each other
parallelExecution in Test := false

assemblyExcludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
      val excludes = Set(
        "jsp-api-2.1-6.1.14.jar",
        "jsp-2.1-6.1.14.jar",
        "jasper-compiler-5.5.12.jar",
        "commons-beanutils-core-1.8.0.jar",
        "commons-beanutils-1.7.0.jar",
        "servlet-api-2.5-20081211.jar",
        "servlet-api-2.5.jar"
      ) 
      cp filter { jar => excludes(jar.data.getName) }
}

// Borrowed from twitter/scalding; used to resolve the conflicts that occur when
// creating a fat jar with sbt-assembly.
assemblyMergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    // case "project.clj" => MergeStrategy.discard // Leiningen build files
    case x if x.startsWith("META-INF") => MergeStrategy.discard // Bumf
    case x if x.endsWith(".html") => MergeStrategy.discard // More bumf
    case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
    // For Log$Logger.class
    case x => old(x)
  }
}
