# Introduction

A simple tutorial for getting started with Spark on EMR. This can also be used as a templete for setting up the local dev environmemnt.

## Dependencies

- [Spark 1.2.0] (http://d3kbcqa49mib13.cloudfront.net/spark-1.2.0-bin-hadoop2.4.tgz) -- For testing your applications locally.
- [AWS CLI] (http://docs.aws.amazon.com/cli/latest/userguide/installing.html)
- [SBT] (http://www.scala-sbt.org/release/tutorial/Setup.html)

## Building

- Get started with Spark Dev Environment
- Get started with Spark on EMR
- Build a spark program
- Run it locally
- Run it on EMR
- Intro to SparkSQL
- Tutorial on using Parquet and Avro
- Using Parquet with SparkSQL


```
aws emr create-cluster --name abond-spark --ami-version 3.3.1 --instance-type m3.xlarge --instance-count 1 \
--ec2-attributes KeyName=abond-kp2 \
--bootstrap-actions Path=s3://support.elasticmapreduce/spark/install-spark -v 1.2
```