#!/bin/sh 
DEMO="FIS Pipeline Demo"
AUTHORS="Christina Lin"
PRODUCT="JBoss Fuse, Container Development Kit, OpenShift"
CDK_HOME=./target
SRC_DIR=./installs
SUPPORT_DIR=./support
PRJ_DIR=./projects
VERSION=2.0.0-beta5

# wipe screen.
clear 

echo
echo "###########################################################################"
echo "##                                                                       ##"   
echo "##  Setting up the ${DEMO}                                 ##"
echo "##                                                                       ##"   
echo "##                                                                       ##"   
echo "##   ##### #   # ##### #####                                             ##"
echo "##   #     #   # #     #                                                 ##"
echo "##   ##### #   # # # # #####                                             ##"
echo "##   #     #   #     # #                                                 ##"
echo "##   #      ###  ##### #####                                             ##"
echo "##                                                                       ##"   
echo "##   ##### #   # ##### ##### ##### ##### ##### ##### ##### ##### #   #   ##"
echo "##     #   ##  #   #   #     #     #   # #   #   #     #   #   # ##  #   ##"
echo "##     #   # # #   #   ##### #  ## ##### #####   #     #   #   # # # #   ##"
echo "##     #   #  ##   #   #     #   # #  #  #   #   #     #   #   # #  ##   ##"
echo "##   ##### #   #   #   ##### ##### #   # #   #   #   ##### ##### #   #   ##"   
echo "##                                                                       ##"   
echo "##   ##### ##### ##### #   # ##### ##### #####                           ##"
echo "##   #     #     #   # #   #   #   #     #                               ##"
echo "##   # # # ##### ##### #   #   #   #     #####                           ##"
echo "##       # #     #  #   # #    #   #     #                               ##"
echo "##   ##### ##### #   #   #   ##### ##### #####                           ##"
echo "##                                                                       ##"
echo "##  brought to you by ${AUTHORS}     ##"
echo "##                                                                       ##"   
echo "##                                                                       ##"   
echo "###########################################################################"
echo

echo "Start installing microservices in the environment"
echo 
echo "Installing Traditional Banking "
echo 
oc new-app -f fisdemo-template-account.json
echo "Installing Block Chain gateway "
echo 
oc new-app -f fisdemo-template-blockchain.json
echo "Installing Template "
echo 
oc new-app -f fisdemo-template.json
