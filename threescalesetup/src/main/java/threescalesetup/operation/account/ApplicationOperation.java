package threescalesetup.operation.account;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.account.Application;
import threescalesetup.dto.api.Threescale;
import threescalesetup.operation.ThreescaleManagment;

public class ApplicationOperation {
	
	Logger log = Logger.getLogger(ApplicationOperation.class.getName());
	
	String SERVICE_URL = "admin/api/accounts/";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public ApplicationOperation(String hostName, String accountid){
		apiurl = SERVICE_URL+accountid+"/applications";
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public String create(String accessToken, String applicationplanid, String name, String description){
		String url = apiurl+".json";
		String applicationid = null;
		
		Application application= new Application();
		application.setAccessToken(accessToken);
		application.setApplicationplanid(applicationplanid);
		application.setName(name);
		application.setDescription(description);
		
		try {
			String response = threescalemanagement.sendPost(url, application.getAllParam());
		
			//System.out.println(response);
			log.fine(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode applicationNode = rootNode.path("application");
			applicationid = applicationNode.get("id").asText();
			//System.out.println("Application created. ID :["+applicationid+"]");
			log.log(Level.FINE, "Application created. ID :[{0}]", applicationid);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationid;
	}
	
	public void delete(String accessToken, String applicationid){
		String url = apiurl+"/"+applicationid+".json";
		Threescale application = new Threescale();
		application.setAccessToken(accessToken);
		
		try {
			threescalemanagement.sendDelete(url,application.getAllParam());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
