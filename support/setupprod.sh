#oc login -u developer
oc new-project fisdemoprod --display-name="Fuse Banking Demo - PROD" --description="Production environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
#oc create -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq62-basic.json

oc project fisdemoprod

#Uncomment if you are using minishift



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
oc process -f support/projecttemplates/template-prod.yml | oc apply -f -

#oc secret new-basicauth apicast-configuration-url-secret --password=https://223b7a0983bc7a596a2bcd067ec7bab7399ee2637228274997632d346a198642@fusedemo-admin.3scale.net
#oc new-app -f support/amptemplates/apicast.yml
