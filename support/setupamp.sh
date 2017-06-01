echo "Install the 3scale on premise on CDK"
echo 

echo "Setup PV for 3scale"
echo 
oc login -u system:admin
oc create -f amptemplates/pv.yml

echo "Install actual backend system"
echo 
oc login -u developer
oc new-project threescaleonprem --display-name="3scale API Management Admin" --description="3scale API management administration system on prem version"
oc new-app -f amptemplates/amp.yml --param WILDCARD_DOMAIN=192.168.64.2.nip.io
