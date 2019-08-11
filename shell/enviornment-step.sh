#!/bin/bash

#Script to create nodes if not created
workdir=`pwd`
if [ -d $workdir/zalenium ] || [ `echo $workdir | awk -F '/' '{print$NF}' | grep '^zalenium'` ]
then
git pull origin master
else
git clone https://github.com/rohitverma1091/zalenium.git
cd zalenium
fi
if [ `eksctl get cluster -n Zalenium` ]
then
node=`eksctl get nodegroup --cluster Zalenium -n standard-workers-new`
node1=`eksctl get nodegroup --cluster Zalenium -n standard-workers`
if [ "$node" == "No nodegroups found" ]
then
eksctl create nodegroup \
--cluster Zalenium \
--version auto \
--name standard-workers-new \
--node-type c4.4xlarge \
--node-ami auto \
--nodes 3 \
--nodes-min 3 \
--nodes-max 4 \
--node-labels app=zalenium
fi
if [ "$node1" == "No nodegroups found" ]
then
eksctl create nodegroup \
--cluster Zalenium \
--version auto \
--name standard-workers \
--node-type t2.medium \
--node-ami auto \
--nodes 1 \
--nodes-min 1 \
--nodes-max 2 \
--node-labels
kubectl create namespace zalenium
kubectl -namespace zalenium apply -f $workdir/kubernetes-template/jenkins-cluster.yml
kubectl -namespace zalenium apply -f $workdir/kubernetes-template/zalenium-cluster.yml
fi
else
eksctl create cluster \
--name Zalenium \
--version 1.13 \
--nodegroup-name standard-workers \
--node-type t2.medium \
--nodes 1 \
--nodes-min 1 \
--nodes-max 2 \
--node-ami auto

eksctl create nodegroup \
--cluster Zalenium \
--version auto \
--name standard-workers-new \
--node-type c4.4xlarge \
--node-ami auto \
--nodes 3 \
--nodes-min 3 \
--nodes-max 4 \
--node-labels app=zalenium
kubectl create namespace zalenium
kubectl -namespace zalenium apply -f $workdir/kubernetes-template/jenkins-cluster.yml
kubectl -namespace zalenium apply -f $workdir/kubernetes-template/zalenium-cluster.yml
fi




