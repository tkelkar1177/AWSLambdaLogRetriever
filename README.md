Author: Tanmay Sandeep Kelkar

The following project consists of 3 parts:

1. TSAvailability
2. InvokeLambdaFuncRest
3. InvokeLambdaFuncgRPC

TSAvailability:

This is a Java project. This has been deployed as a Lambda function and need not be run locally. The aim of this is to fulfill the task where we search for logs in a log file stored on S3 given a timestamp and a small time delta and return the logs within that time range which have a matching regex pattern as given in the LogFileGenerator.

InvokeLambdaFuncRest:

This project invokes the Lambda function by making a POST call with to the API endpoint exposed through the API Gateway. This call is passed along with a JSON body consisting of the parameters "timestamp" and "dt". This returns the logs which satisfy the condition for the above task and creates a new text file in the project directory with the returned logs. If there is no satisfying log, this we obtain an MD5 hash code to indicate this.
This project can be run by running "sbt clean compile run" in the project directory.

InvokeLambdaFuncgRPC:

This project invokes the Lambda function where a client makes a call to a server, which in turn is listening for calls from the client to then make a POST call to the API endpoint exposed by the API Gateway. This returns the logs which satisfy the condition for the above task and creates a new text file in the project directory with the returned logs. If there is no satisfying log, this we obtain an MD5 hash code to indicate this.
This project can be run by running 'sbt "runMain.Client"' for the client stub and 'sbt "runMain.grpcServer"' for the server stub in a terminal in the project directory.

The LogFileGenerator is deployed on an EC2 instance which produces logs in a text file stored on S3.

The Youtube video of the demo of this project can be found here: https://youtu.be/QLcx1Fujrmc
