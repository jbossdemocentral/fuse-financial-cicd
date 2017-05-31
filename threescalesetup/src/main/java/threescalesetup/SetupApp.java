package threescalesetup;

import java.util.logging.Level;
import java.util.logging.Logger;

import threescalesetup.operation.api.ApplicationPlanOperation;
import threescalesetup.operation.api.LimitOperation;
import threescalesetup.operation.api.MetricsOperation;
import threescalesetup.operation.api.ServiceOperation;

public class SetupApp {

	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(SetupApp.class.getName()); 
		
		
		String threescalehost = args[0];
		String accessToken = args[1];
		String name = args[2];
		String servicename = args[3];
		String isSelfmanage = args[4];
		Boolean isSelfmanageBoolean = false;
		String applicationplanname = args[5];
		String description = args[6];
		
		String serviceid="2555417742499";
		String applicationplanid="2357355894898";
		String hitsMetricsid="";
		
		ServiceOperation serviceOperation = new ServiceOperation(threescalehost);
		if("true".equalsIgnoreCase(isSelfmanage) ){
			isSelfmanageBoolean=true;
		}
		
		serviceid = serviceOperation.create(accessToken, name,  servicename,  description,  isSelfmanageBoolean);
		
		ApplicationPlanOperation applicationPlanOperation = new ApplicationPlanOperation(threescalehost,serviceid);
		applicationplanid = applicationPlanOperation.create(accessToken, applicationplanname);
		
		MetricsOperation metricsOperation = new MetricsOperation(threescalehost,serviceid);
		hitsMetricsid = metricsOperation.get(accessToken).get("hits");
		
		LimitOperation limitOperation = new LimitOperation(threescalehost,applicationplanid,hitsMetricsid);
		String limitid =  limitOperation.create(accessToken);
		
		log.info("-----------------IMPROTANT REFERENCE-------------------");
		log.info("--Successfully setup 3scale Service API..");
		log.log(Level.INFO,"--Created service [{0}] id: [{1}]....",new Object[]{ name, serviceid });
		log.log(Level.INFO,"--Created application plan [{0}] id: [{1}]....",new Object[]{ applicationplanname, applicationplanid });
		log.log(Level.INFO,"--Created limit for Hits Metrics [{0}] id: [{1}]....",new Object[]{ hitsMetricsid, limitid });
		log.info("-----------------IMPROTANT REFERENCE-------------------");
		
		
	}

}
