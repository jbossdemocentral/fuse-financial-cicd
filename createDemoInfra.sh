# Login with developer account
oc login -u developer

# Create fisdemo project
oc new-project fisdemo --description="Financial Information System" --display-name="FIS Demo"

# Create A-MQ template
oc create -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq62-basic.json

# Create Jenkins
oc new-app --template=jenkins-ephemeral --param=JENKINS_PASSWORD=password

# Create MySQL
oc new-app --template=mysql-ephemeral --param=MYSQL_PASSWORD=password --param=MYSQL_USER=dbuser --param=MYSQL_DATABASE=sampledb

# Create A-MQ
oc new-app --template=amq62-basic --param=MQ_USERNAME=admin --param=MQ_PASSWORD=admin
