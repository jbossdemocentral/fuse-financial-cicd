package threescalesetup.operation.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.api.Threescale;
import threescalesetup.operation.ThreescaleManagment;

public class MetricsOperation {

	String SERVICE_URL = "admin/api/services/";
	ThreescaleManagment threescalemanagement;
	
	public MetricsOperation(String hostName, String serviceid){
		SERVICE_URL = SERVICE_URL+serviceid+"/metrics.json";
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public Map<String,String> get(String accessToken){
		
		Map<String,String> metricIds = new HashMap<String,String>();
		
		Threescale threescale = new Threescale();
		threescale.setAccessToken(accessToken);
		
		
		try {
			String response = threescalemanagement.sendGet(this.SERVICE_URL+threescale.getQuery());
		
			System.out.println(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode metricsNode = rootNode.path("metrics");
			Iterator<JsonNode> metrics = metricsNode.elements();
			while(metrics.hasNext()){
				JsonNode metricNode = metrics.next();
				String metricId = metricNode.get("metric").get("id").asText();
				String metricName = metricNode.get("metric").get("name").asText();
				metricIds.put(metricName,metricId);
				
				System.out.println("Found metrics ["+metricName+"]:["+metricId+"]");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metricIds;
	}
	

}
