echo "Install the FIS 2.0 image stream"
echo 

#oc login -u system:admin
#oc project openshift
#oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/master/fis-image-streams.json -n openshift
#oc import-image amq62-openshift --from=registry.access.redhat.com/jboss-amq-6/amq62-openshift -n openshift --confirm


echo "Create fisdemo project to work in, and load the A-MQ 6.2 template"
echo 


oc login -u developer
oc new-project fisdemo --display-name="Fuse Banking Demo - Dev and UAT" --description="Development and UAT environment for Agile Integration Banking Demo - Power by Red Hat Fuse"


echo "Setup the surrounding softwate and environment"
echo
echo "Start up MySQL for database access"
oc create -f https://raw.githubusercontent.com/openshift/origin/master/examples/db-templates/mysql-ephemeral-template.json
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb

echo "Start up Broker for bitcoin gateway"
oc import-image amq62-openshift --from=registry.access.redhat.com/jboss-amq-6/amq62-openshift --confirm
oc create -f projecttemplates/amq62-openshift.json
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin --param=IMAGE_STREAM_NAMESPACE=fisdemo


echo "Create Traditional Banking instance"
cd ../fisdemoaccount/
mvn fabric8:deploy -Dmysql-service-username=dbuser -Dmysql-service-password=password

echo "Create Block Chain Banking instance"
cd ../fisdemoblockchain/
mvn fabric8:deploy

cd ..
oc process -f support/projecttemplates/template-uat.yml | oc create -f -
