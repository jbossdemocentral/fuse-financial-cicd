package threescalesetup;

import java.util.logging.Level;
import java.util.logging.Logger;

import threescalesetup.operation.api.ApplicationPlanOperation;
import threescalesetup.operation.api.LimitOperation;
import threescalesetup.operation.api.ServiceOperation;

public class ResetApp {
	
	public static void main(String[] args) {
		Logger log = Logger.getLogger(ResetApp.class.getName());
		String threescalehost=args[0];
		String accessToken=args[1];
		String serviceid=args[2];
		String applicationplanid=args[3];
		String hitsMetricsid=args[4];
		String limitid=args[5];
		
		LimitOperation limitOperation = new LimitOperation(threescalehost,applicationplanid,hitsMetricsid);
		ApplicationPlanOperation applicationPlanOperation = new ApplicationPlanOperation(threescalehost,serviceid);
		ServiceOperation serviceOperation = new ServiceOperation(threescalehost);
		
		limitOperation.delete(accessToken,limitid);
		applicationPlanOperation.delete(accessToken,applicationplanid);
		serviceOperation.delete(accessToken, serviceid);
		
		log.info("--Successfully reset 3scale Accounts..");
		log.log(Level.INFO,"--Service [{0}] , application plan [{1}] and limit [{2}] has been deleted....",new Object[]{ serviceid, applicationplanid, limitid});
		
		
	}
}
