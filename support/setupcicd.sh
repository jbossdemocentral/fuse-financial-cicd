echo "This script prepare the CICD project for you"


echo "Create a project to all pipelines"
echo 

oc new-project fisdemocicd --display-name="Fuse Banking Pipeline" --description="All CI/CD Pipeline for Banking Demo"


echo "Grant access to cicd project user so it can operate on UAT and PROD env"
echo 

oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemo
oc policy add-role-to-group edit system:serviceaccounts:fisdemocicd -n fisdemoprod

echo "Install all three pipelines"
echo 
oc create -f pipeline-uat.yml
oc create -f pipeline-ab.yml
oc create -f pipeline-allprod.yml
