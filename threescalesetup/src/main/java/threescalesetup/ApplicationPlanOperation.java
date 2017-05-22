package threescalesetup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.ApplicationPlan;
import threescalesetup.dto.Threescale;

public class ApplicationPlanOperation {
	String SERVICE_URL = "admin/api/services/";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public ApplicationPlanOperation(String hostName, String serviceid){
		apiurl = SERVICE_URL+serviceid+"/application_plans";
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public String create(String accessToken, String name){
		String url = apiurl+".json";
		String applicationPlanid = null;
		
		ApplicationPlan applicationplan = new ApplicationPlan();
		applicationplan.setAccessToken(accessToken);
		applicationplan.setName(name);
		
		try {
			String response = threescalemanagement.sendPost(url, applicationplan.getAllParam());
		
			System.out.println(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode serviceNode = rootNode.path("application_plan");
			applicationPlanid = serviceNode.get("id").asText();
			System.out.println("Application plan Created with id:["+applicationPlanid+"]");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationPlanid;
	}
	
	public void delete(String accessToken, String applicationplanid){
		String url = apiurl+"/"+applicationplanid+".json";
		Threescale applicationPlan = new Threescale();
		applicationPlan.setAccessToken(accessToken);
		
		try {
			threescalemanagement.sendDelete(url,applicationPlan.getAllParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
