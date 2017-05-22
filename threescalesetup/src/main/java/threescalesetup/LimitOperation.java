package threescalesetup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.Limit;
import threescalesetup.dto.Threescale;

public class LimitOperation {
	String SERVICE_URL = "admin/api/application_plans/";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public LimitOperation(String hostName, String appplanid, String metricid){
		apiurl = SERVICE_URL+appplanid+"/metrics/"+metricid+"/limits";
		threescalemanagement = new ThreescaleManagment(hostName);
		
	}
	
	
	public String create(String accessToken){
		String url = apiurl+".json";
		String limitPlanid = null;
		
		Limit limit = new Limit();
		limit.setAccessToken(accessToken);
		limit.setPeriod(Limit.PERIOD_MINUTE);
		limit.setValue("50");
		try {
			String response = threescalemanagement.sendPost(url, limit.getAllParam());
		
			System.out.println(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode limitNode = rootNode.path("limit");
			limitPlanid = limitNode.get("id").asText();
			System.out.println("Limit Created with id:["+limitPlanid+"]");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return limitPlanid;
	} 
	
	public void delete(String accessToken, String limitid){
		String url = apiurl+"/"+limitid+".json";
		Threescale limit = new Threescale();
		limit.setAccessToken(accessToken);
		
		try {
			threescalemanagement.sendDelete(url,limit.getAllParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
