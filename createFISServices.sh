oc login -u developer
oc project fisdemo
oc create -f fisdemo-template-account.json
oc create -f fisdemo-template-blockchain.json
oc create -f fisdemo-template.json

oc new-app --template=fisaccount-service --param=GIT_REPO=https://github.com/gnunn1/financepipeline.git
oc new-app --template=fisblockchain-service --param=GIT_REPO=https://github.com/gnunn1/financepipeline.git 
oc new-app --template=fisgateway-service --param=GIT_REPO=https://github.com/gnunn1/financepipeline.git
