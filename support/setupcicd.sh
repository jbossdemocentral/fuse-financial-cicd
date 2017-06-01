echo "This script prepare the CICD project for you"


echo "Create a project to all pipelines"
echo 

echo "Create a project to all pipelines"
echo 

oc new-project fisdemocicd --display-name="Fuse Banking Pipeline" --description="All CI/CD Pipeline for Banking Demo"


echo "Grant access to cicd project user so it can operate on UAT and PROD env"
echo 

oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemo
oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemoprod

echo "Start up JENKINS for pipeline...."
oc new-app jenkins-ephemeral

echo "Install all three pipelines"
echo 
oc create -f pipelinetemplates/pipeline-uat.yml
oc create -f pipelinetemplates/pipeline-ab.yml
oc create -f pipelinetemplates/pipeline-allprod.yml

oc new-app pipeline-uat

oc new-app pipeline-ab \
--param=THREESCALE_URL=https://fusedemo-admin.3scale.net \
--param=API_TOKEN=143d1a90ea5d369a88ec35310f06b86fec8569a1c34c21eaffc9f9906947962a \
--param=APP_PLAN_ID=2357355899471 \
--param=METRICS_ID=2555418019263 \
--param=API_LIMITS=25 \
--param=OPENSHIFT_HOST=192.168.64.2.nip.io

oc new-app pipeline-allprod \
--param=THREESCALE_URL=https://fusedemo-admin.3scale.net \
--param=API_TOKEN=143d1a90ea5d369a88ec35310f06b86fec8569a1c34c21eaffc9f9906947962a \
--param=APP_PLAN_ID=2357355899471 \
--param=METRICS_ID=2555418019263 \
--param=API_LIMITS=50 \
--param=OPENSHIFT_HOST=192.168.64.2.nip.io