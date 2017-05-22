package threescalesetup;

public class SetupApp {

	public static void main(String[] args) {
		String threescalehost = args[0];
		String accessToken = args[1];
		String name = args[2];
		String servicename = args[3];
		String isSelfmanage = args[4];
		Boolean isSelfmanageBoolean = false;
		String description = args[5];
		String applicationplanname = args[6];
		
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
		limitOperation.create(accessToken);
	}

}
