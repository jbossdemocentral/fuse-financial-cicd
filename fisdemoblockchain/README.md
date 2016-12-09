# Spring Boot, Camel and ActiveMQ QuickStart

This quickstart demonstrates how to connect a Spring-Boot application to an ActiveMQ broker and use JMS messaging between two Camel routes using Kubernetes or OpenShift.

In this example we will use two containers, one container to run as a ActiveMQ broker, and another as a client to the broker, where the Camel routes is running.

This quickstart requires the ActiveMQ broker has been deployed and running first. This can be done from the web console from the `Apps` page, and then install the `messaging` application.

### Building

The example can be built with

    mvn clean install


### Running the example locally

The example can be run locally using the following Maven goal:

    mvn spring-boot:run


### Running the example in Kubernetes

It is assumed a running Kubernetes platform is already running. If not you can find details how to [get started](http://fabric8.io/guide/getStarted/index.html).

Assuming your current shell is connected to Kubernetes or OpenShift so that you can type a command like

```
kubectl get pods
```

or for OpenShift

```
oc get pods
```

Then the following command will package your app and run it on Kubernetes:

```
mvn fabric8:run
```

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the [fabric8 developer console](http://fabric8.io/guide/console.html) to manage the running pods, and view logs and much more.


### More details

You can find more details about running this [quickstart](http://fabric8.io/guide/quickstarts/running.html) on the website. This also includes instructions how to change the Docker image user and registry.

