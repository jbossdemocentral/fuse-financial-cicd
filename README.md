# FIS 2.0 Financial Demo

This Financial demo is a simple gateway that redirect the incoming request of 
 - checking balance
 - transfer money 
to different money source, one pass to a triditional banking microservice app, which interact directly with MySQL database, and pass the bitcoin request to the other microservice application connecting to a mock-blockchain through messaging broker. 

There are many aspect with this demo, 
1. Source to Image (S2i) build and deploy process
2. Exposing RESTAPI using Camel, and export API doc to swagger
3. Building a pipeline to support automated CI/CD
4. 

but first, let's start with setting up the application. 

## Setting up OpenShift 
Install CDK version x.x with OpenShift 3.3 (TODO -- need OpenShift link) 
Start up your local OpenShift environment by running 
'
oc cluster up
'
Then login as system admin to install the FIS 2.0 Tech preview image stream. (NO need when G.A.)
'
oc login -u system:admin
oc project openshift
oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.0.redhat-000026/fis-image-streams.json
'
And log back in as developer, install the messaging template that we will use later in the process. 

'
oc login -u developer
oc new-project fisdemo
oc create -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq62-basic.json
'

## Setup MySql database, A-MQ broker and Jenkins 

You can either setup all of them using GUI on OpenShift console, or using command line as follows

'
TODO
'
## Pushing application to OpenShift 
Download the git repository by either forking it, or simply cloneing it. 
'
git clone xxx.git
'
For the two microservice 
 - Traditional Bankling
 - Bitcoin Gateway
We will using the Binary S2i to upload the application. 
Go to your traditional banking account project folder, and run
'
cd fisdemoaccount
mvn fabric8:deploy
'
Do the same to the bitcoin gateway under it's project folder 
'
cd ..
cd fisdemoblockchain
mvn fabric8:deploy
'
After successfully install the application, it's time for use to deploy the API Gateway. This time, we are going to build a pipeline, that goes through and automated the CI/CD process from staging to UAT. 

'
oc create -f 
'



