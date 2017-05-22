package threescalesetup.operation.account;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import threescalesetup.dto.api.Threescale;
import threescalesetup.operation.ThreescaleManagment;

public class AccountOperation {
	String SERVICE_URL = "admin/api/accounts";
	String apiurl = "";
	ThreescaleManagment threescalemanagement;
	
	public AccountOperation(String hostName){
		apiurl = SERVICE_URL;
		threescalemanagement = new ThreescaleManagment(hostName);
	}
	
	
	public Map<String,String> get(String accessToken){
		
		Map<String,String> accountIds = new HashMap<String,String>();
		String url = apiurl+".json";
		
		
		Threescale threescale = new Threescale();
		threescale.setAccessToken(accessToken);
		
		try {
			String response = threescalemanagement.sendGet(url+threescale.getQuery());
			
			System.out.println(response);
			
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode accountsNode = rootNode.path("accounts");
			Iterator<JsonNode> accounts = accountsNode.elements();
			while(accounts.hasNext()){
				JsonNode accountNode = accounts.next();
				String accountId = accountNode.get("account").get("id").asText();
				String orgName = accountNode.get("account").get("org_name").asText();
				accountIds.put(orgName,accountId);
				
				System.out.println("Found metrics ["+orgName+"]:["+accountId+"]");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountIds;
	}
	
	
}
