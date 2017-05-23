# 3scale Demo Setup Toolkit

This simple java application is a very simple Java application which helps developer to setup their 3scale environment and resets if needed. 

Make sure you have a valid 3scale account or have access to a 3scale admin environment. Therefore you to obtain two of following key to continue

***threescalehostdomain*** - Domain of your 3scale admin portal as in 3scale_id-admin.3scale.net.

***accesstoken*** - It can be set in admin console, top right hand corner, select *Personal Setting*, click on Tokens on the top tab, and click on *Add Access Token*.
	
 Create the token by settin the following information
 
  - **Name**: demomgmttoken
  - **Scopes**: Account Management API
  - **Permission**: Read & Write

Rember the generated access token and don't lose it! 

 
## Setup

This will create a service , with application plan and one limits assign to hits.

**Parameters**

- threescalehostdomain :  Domain of your 3scale admin portal as in 3scale_id-admin.3scale.net.
- accessToken : Management Access token
- name : Name you want yo call your service
- servicename : System name of your service *Note: Only ASCII letters, numbers, dashes and underscores are allowed.*
- isSelfmanage :	true or false. Select if you are managing your own gateway
- applicationplanname : Name of your application plan
- servicedescription : Short description of your service - *optional*


```
mvn exec:java -Dexec.mainClass=threescalesetup.SetupApp -Dexec.args="<threescalehostdomain> <accessToken> <name> <servicename> <isSelfmanage> <applicationplanname> <servicedescription>" 
```

For example: 

```
mvn exec:java -Dexec.mainClass=threescalesetup.SetupApp -Dexec.args="myfusedemo 14sadasdasds1c34c21eaffc9f9906947962a financeapidemo financeapidemo true productiondemo 'Finance API Demo for Agile Integration'" 
```

If everything goes right, you should be able to get following response message. Remeber those as you might need it to reset your 3scale setting.

Now it's time to setup the application and add to an account, in this case, the tool will automatically find the default "Developer" account and create the application under it. 

**Parameters**

- threescalehostdomain :  Domain of your 3scale admin portal as in 3scale_id-admin.3scale.net.
- accessToken : Management Access token
- applicationplanid : The application plan ID you wish assolciate your application to
- applicationname : Name the application you are creating
- applicationdescription : Short description of your application 

```
mvn exec:java -Dexec.mainClass=threescalesetup.SetupAccount -Dexec.args="<threescalehostdomain> <accessToken> <applicationplanid> <applicationname> <applicationdescription>"
```

For example: 

```
mvn exec:java -Dexec.mainClass=threescalesetup.SetupAccount -Dexec.args=myfusedemo 14sadasdasds1c34c21eaffc9f9906947962a 2357355899155 financedemoapp 'The Finance Demo Application'"
```

## Reset

This undo everything we did in the setup, so it will require following information from either the setup app or from your admin console
**Parameters**

- threescalehost:  Domain of your 3scale admin portal as in 3scale_id-admin.3scale.net.
- accessToken : Management Access token 
- serviceid : The service ID you wish to delete
- applicationplanid : The application plan  ID you wish to delete attached to the service
- hitsMetricsid : The hits meterics ID of your service
- limitid : The limit ID you wish to delete attached to the service
		
```
mvn exec:java -Dexec.mainClass=threescalesetup.ResetApp -Dexec.args="<threescalehost> <accessToken> <serviceid> <applicationplanid> <hitsMetricsid> <limitid>"
```

For example:

```
mvn exec:java -Dexec.mainClass=threescalesetup.ResetApp -Dexec.args="myfusedemo 14sadasdasds1c34c21eaffc9f9906947962a 2555417744576 2357355897629 2555418018315 2639695724349"
```

And to reset your account status, in this case, the tool will only clean up the applications under the default "Developer" account

**Parameters**

- threescalehost:  Domain of your 3scale admin portal as in 3scale_id-admin.3scale.net.
- accessToken : Management Access token 
- applicationid : The application ID you wish to delete


```
mvn exec:java -Dexec.mainClass=threescalesetup.ResetAccount -Dexec.args="<threescalehost> <accessToken> <applicationid>"
```

For example:

```
mvn exec:java -Dexec.mainClass=threescalesetup.ResetAccount -Dexec.args="myfusedemo 14sadasdasds1c34c21eaffc9f9906947962a 1409614872563"
```