package threescalesetup;

public class ResetApp {

	public static void main(String[] args) {
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
		
		
		
	}

}
