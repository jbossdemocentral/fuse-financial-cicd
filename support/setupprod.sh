g
#oc login -u developer
oc new-project fisdemoprod --display-name="Fuse Banking Demo - PROD" --description="Production environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
#oc create -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq62-basic.json

oc project fisdemoprod

echo "Setup the surrounding softwate and environment"
echo
echo "Start up JENKINS for pipeline...."
oc new-app jenkins-ephemeral --param=JENKINS_PASSWORD=password
echo "Start up MySQL for database access"
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb
echo "Start up Broker for bitcoin gateway"
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin

echo "Create Traditional Banking instance"
cd ../fisdemoaccount/
mvn fabric8:deploy -Dmysql-service-username=dbuser -Dmysql-service-password=password

echo "Create Block Chain Banking instance"
cd ../fisdemoblockchain/
mvn fabric8:deploy

cd ..
oc process -f template-prod.yml | oc create -f -
oc process -f support/kubeflix.yml | oc create -f -