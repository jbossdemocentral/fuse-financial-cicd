echo "Create fisdemo project to work in, and load the A-MQ 6.2 template"
echo 
oc login -u developer
oc new-project fisdemo
oc create -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq62-basic.json

echo "Setup the surrounding softwate and environment"
echo
echo "Start up JENKINS for pipeline...."
oc new-app jenkins-ephemeral --param=JENKINS_PASSWORD=password
echo "Start up MySQL for database access"
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb
echo "Start up Broker for bitcoin gateway"
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin


