# Introduction

A simple tutorial for getting started with Spark on EMR. This can also be used as a templete for setting up the local dev environmemnt.

## Dependencies

- [Spark 1.2.0] (http://d3kbcqa49mib13.cloudfront.net/spark-1.2.0-bin-hadoop2.4.tgz) -- For testing your applications locally.
- [AWS CLI] (http://docs.aws.amazon.com/cli/latest/userguide/installing.html)
- [SBT] (http://www.scala-sbt.org/release/tutorial/Setup.html)

## Building

### Get started with Spark Dev Environment

```
git clone https://github.com/abhibond/spark-emr-example.git
cd spark-emr-example
sbt assembly
```

## Running

### Run it locally


```
YOUR_SPARK_HOME/bin/spark-submit \
--class "com.abhibon.spark.WordCount" \
--master local[4] \
/Path-To-Your-Fat-Jar Arguments
```

### Run it on EMR


#### Launching EMR with Spark

Always try to launch emr cluster with the latest Spark version. For more details check [here] (https://github.com/awslabs/emr-bootstrap-actions/tree/master/spark).

```
aws emr create-cluster --name abond-spark --ami-version 3.3.1 --instance-type m3.xlarge --instance-count 1 \
--ec2-attributes KeyName=abond-kp2 \
--bootstrap-actions Path=s3://support.elasticmapreduce/spark/install-spark -v 1.2 -g
```

#### Executing app on EMR

We submit a job to Spark via spark-submit. This can be done by adding a step to the EMR using the [script-runner] (http://docs.aws.amazon.com/ElasticMapReduce/latest/DeveloperGuide/emr-hadoop-script.html) interface.


```
aws emr add-steps --cluster-id <YOUR_CLUSTER_ID> --steps \
Name=WordCount,Jar=s3://elasticmapreduce/libs/script-runner/script-runner.jar,Args=[/home/hadoop/spark/bin/spark-submit,--deploy-mode,cluster,--master,yarn-cluster,--class,com.abhibon.spark.WordCount,s3://abond-dev/spark-demo/bin/spark-emr-example.jar],ActionOnFailure=CONTINUE
```

For more examples check [here] (https://github.com/awslabs/emr-bootstrap-actions/blob/master/spark/examples/spark-submit-via-step.md)


## To-DO

- Intro to SparkSQL
- Tutorial on using Parquet and Avro with Spark
- Using Parquet with SparkSQL

## Resources

- [Spark Programming Guide] (http://spark.apache.org/docs/latest/programming-guide.html)
- [Submitting Spark Applications] (https://spark.apache.org/docs/1.1.1/submitting-applications.html)
- [Spark Cluster Mode] (https://spark.apache.org/docs/1.1.1/cluster-overview.html)
- [Spark Scala Docs] (http://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.package)
