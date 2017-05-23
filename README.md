# FIS 2.0 Financial Agile Integration Demo

This Financial demo is a simple gateway that redirect the incoming request of 
 - Checking balance
 - Transfer money 
to different money source, one pass to a traditional banking microservice app, which interact directly with MySQL database, and pass the bitcoin request to the other microservice application connecting to a mock-blockchain through messaging broker. 

![alt text](images/outline.png "outline")

There are many aspect with this demo, 
1. Source to Image (S2i) build and deploy process
2. Building a pipeline to support automated CI/CD
3. Exposing RESTAPI using Camel, and export API doc to swagger
4. Manage API through 3scale API management
5. Running Hystrix among APIs 


but first, let's start with setting up the application. 

## Setting up OpenShift 
[Install OpenShift Container Platform 3.4] (https://github.com/redhatdemocentral/ocp-install-demo) 

Start up your local OpenShift environment by running 
	
```
oc cluster up 
```

Then login as system admin to install the FIS 2.0 image stream.
	
```
oc login -u system:admin
oc project openshift
oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/master/fis-image-streams.json
```

And log back in as developer, install the messaging template that we will use later in the process. 

	
```
oc login -u developer
oc new-project fisdemo --display-name="Fuse Banking Demo - Dev and UAT" --description="Development and UAT environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
oc create -f support/projecttemplates/amq62-openshift.json
```

## Setup MySql database, A-MQ broker and Jenkins 

You can either setup all of them using GUI on OpenShift console, or using command line as follows

	
```
oc create -f https://raw.githubusercontent.com/openshift/origin/master/examples/db-templates/mysql-ephemeral-template.json
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin
```

## Pushing application to OpenShift 
Download the git repository by either forking it, or simply cloning it. 
(My suggesting is to fork it, if you want to play with the code)
	
```
git https://github.com/YOUR_RPEO/fuse-financial-cicd.git
```
For the two microservice 
 - Traditional Bankling
 - Bitcoin Gateway
We will using the Binary S2i to upload the application. 
Go to your traditional banking account project folder, and run

	
```
cd fisdemoaccount
mvn fabric8:deploy -Dmysql-service-username=dbuser -Dmysql-service-password=password
```


Do the same to the bitcoin gateway under it's project folder 

	
```
cd ..
cd fisdemoblockchain
mvn fabric8:deploy
```

After successfully install the application, it's time to deploy the API Gateway. This time, we are going to build a pipeline, that goes through and automated the CI/CD process from staging to UAT. 

```
oc process -f support/projecttemplates/template-uat.yml | oc create -f -
```

This is optional step, you don't have to install unless you want to see the Hystrix Dashboard

```
oc process -f support/kubeflix.yml | oc create -f -
```

Congradulations! You can now start playing with the demo! 
And here are some of the ways you can play with it! 
In your browser test the following links

```
http://fisgateway-service-fisdemo.192.168.99.100.xip.io/demos/sourcegateway/balance/234567?moneysource=bitcoin
http://fisgateway-service-fisdemo.192.168.99.100.xip.io/demos/sourcegateway/balance/234567
```
## API resiliency with Hystrix 

Spin up the Hystrix dashboard and Turbine server using the provided kubeflix.json template

```
oc process -f support/kubeflix.yml | oc create -f -
```


## Setting up 3scale API Management 
There are two ways for you to setup 3scale 

1. **Option ONE:** (RECOMMANDED) Sign up for a 45 day trial version online, go to 
```
https://www.3scale.net/signup/
```
You will receive a administration domain to manage APIs. 

   **Option TWO:** Spin up local 3scale environment
   
   **WARNING!!! You need at LEAST 16 GB of memories assgined to CDK**
   
   A.  Create a project
	
	```
	oc new-project threescaleonprem
	```
   B.  Setup persistence volume (if you are running with CDK V3/Minishift V1, this is optional)
	```
	oc new-project threescaleonprem
	```
   C.  Install 3scale into the project by excuting following command. The WILDCARD_DOMAIN parameter set to the domain of the OpenShift for your CDK:
   
	```
	oc new-app -f support/amptemplates/amp.yml --param WILDCARD_DOMAIN=<WILDCARD_DOMAIN>
	```
   
   For detail installation, please visit the official installation page. 
	
2. Retreive Access token 
	
	**Option ONE:**
	
	A. In admin console, top right hand corner, select *Personal Setting*, click on Tokens on the top tab, and click on **Add Access Token**.
	
	B. Crate the token by settin the following information
		
	- **Name**: demomgmttoken
	- **Scopes**: Account Management API
	- **Permission**: Read & Write

	Rember the generated access token and don't lose it! 

		
	**Option TWO:**
	
	After successfully installing 3scale backend system on OpenShift, should be provided as part of the result output on the execution console. 


3. Configure 3scale setting, run following script along with your credentials to setup 3scale

	```
	cd threescalesetup
	mvn exec:java -Dexec.mainClass=threescalesetup.SetupApp -Dexec.args="<3SCALE_HOST_DOMAIN> <ACCESS_TOKEN> financeapidemo financeapidemo true productiondemo 'Finance API Demo for Agile Integration'" 
	cd ..
	```
	![alt text](images/threescaleapiconfig.png "3scale configuration")

4. Setup accounts to access the API service.

	```
	cd threescalesetup
	mvn exec:java -Dexec.mainClass=threescalesetup.SetupAccount -Dexec.args=<3SCALE_HOST_DOMAIN> <ACCESS_TOKEN> <APPLICATION_PLAN_ID> financedemoapp 'The Finance Demo Application'
	cd ..
	```

	
5. Install APICast to UAT and PROD projects, with your access token and 3scale admin domain name

	```
	oc project fisdemo
	oc secret new-basicauth apicast-configuration-url-secret --password=https://<ACCESS_TOKEN>@<DOMAIN>-admin.3scale.net

	oc new-app -f support/amptemplates/apicast.yml
	```

	![alt text](images/threescaleinstall.png "3scale install")

6. Update 3scale Integration configuration address

## CI/CD across integration solution

### IMPORTANT!!! Please make sure you have 3scale account setup Following CI/CD A-B Testing pipeline to work. 

![alt text](images/cicd.png "CI/CD pipelines")

Create a Production project for FISDEMO

```
oc new-project fisdemoprod --display-name="Fuse Banking Demo - PROD" --description="Production environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
```

Add setup the environment including supporting microservices and configurations (deployment configs/service/route) in production

```
./support/setupProd.sh 
oc process -f support/projecttemplates/template-prod.yml | oc create -f -
oc process -f support/kubeflix.yml | oc create -f -
```

Create a project to all pipelines

```
oc new-project fisdemocicd --display-name="Fuse Banking Pipeline" --description="All CI/CD Pipeline for Banking Demo"
```

Grant access to cicd project user so it can operate on UAT and PROD env

```
oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemo
oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemoprod
```

Install all three pipelines

```
oc create -f support/pipelinetemplates/pipeline-uat.yml
oc create -f support/pipelinetemplates/pipeline-ab.yml
oc create -f support/pipelinetemplates/pipeline-allprod.yml

oc new-app pipeline-uat
oc new-app pipeline-ab
oc new-app pipeline-allprod
```
The Banking pipeline project includes 3 pipelines demonstrate the possible flow of an integration application of Fuse. 

A. The pre-built UAT pipeline builds the image from SCM (github). and deploy a testing instance onto the platform. Then a pre-UAT test is done by a QA (you), after verification, you can choose to reject the change or promote it to UAT, by tagging the image with uatready flag. When promoted, the pipeline will deploy the uat tagged image on openshift, with UAT route linked to it. 

B. A/B Testing pipeline will move UAT image from the UAT project to Production project by tagging and deploying the image, and allocate 30% of traffic to the new service and 70% to existing stable service. Also updates all traffics from API management layer to 25 calls per minutes. 

C. Ready for full release. The all production pipeline will do the rolling update, old service will be replace by the new service as now become the stable version. All traffic will then redirect to the stable new version of running instance. 

![alt text](images/allpipelines.png "allpipelines")



## Version update notes, and TODOs
- REMOVE UAT pipeline from UAT project into the pipeline Project
- TODO: Add configmaps and secrets
- TODO: An UI to view the result
