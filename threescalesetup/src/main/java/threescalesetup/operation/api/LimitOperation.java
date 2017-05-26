package threescalesetup.operation.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.api.Limit;
import threescalesetup.dto.api.Threescale;
import threescalesetup.operation.ThreescaleManagment;

public class LimitOperation {
	Logger log = Logger.getLogger(LimitOperation.class.getName());
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
		
			//System.out.println(response);
			log.fine(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode limitNode = rootNode.path("limit");
			limitPlanid = limitNode.get("id").asText();
			//System.out.println("Limit created with id:["+limitPlanid+"]");
			log.log(Level.FINE, "Limit created. ID :[{0}]", limitPlanid);	
				
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
