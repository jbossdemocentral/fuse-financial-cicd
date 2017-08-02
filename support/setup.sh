echo "Install the FIS 2.0 image stream"
echo

#oc login -u system:admin
#oc project openshift
#oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/master/fis-image-streams.json -n openshift
#oc import-image amq62-openshift --from=registry.access.redhat.com/jboss-amq-6/amq62-openshift -n openshift --confirm



echo "Create fisdemo project to work in, and load the AMQ 6.2 template"
echo

echo "Create namespace for DEV environment"
echo
#oc login -u developer
oc new-project fisdemo --display-name="Fuse Banking Demo - Dev" --description="Development environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
oc project fisdemo

echo "Setup the surrounding softwate and environment"
echo
echo "Start up MySQL for database access"
oc create -f https://raw.githubusercontent.com/openshift/origin/master/examples/db-templates/mysql-ephemeral-template.json
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb

echo "Start up Broker for bitcoin gateway"

oc create -f projecttemplates/amq62-openshift.json
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin --param=IMAGE_STREAM_NAMESPACE=openshift


oc process -f projecttemplates/template-gateway.yml | oc create -f -
oc process -f projecttemplates/template-fisaccount.yml | oc create -f -
oc process -f projecttemplates/template-fisblockchain.yaml | oc create -f -

echo "Create namespace for UAT environment"
echo
oc new-project fisdemouat --display-name="Fuse Banking Demo - UAT" --description="UAT environment for Agile Integration Banking Demo - Power by Red Hat Fuse"
oc project fisdemouat

echo "Setup the surrounding softwate and environment"
echo
echo "Start up MySQL for database access"
oc create -f https://raw.githubusercontent.com/openshift/origin/master/examples/db-templates/mysql-ephemeral-template.json
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb

echo "Start up Broker for bitcoin gateway"
oc create -f projecttemplates/amq62-openshift.json
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin --param=IMAGE_STREAM_NAMESPACE=openshift

oc process -f projecttemplates/template-gateway-uat.yml | oc create -f -
oc process -f projecttemplates/template-fisaccount-uat.yml | oc create -f -
oc process -f projecttemplates/template-fisblockchain-uat.yaml | oc create -f -

