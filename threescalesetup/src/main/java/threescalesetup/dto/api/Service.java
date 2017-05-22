package threescalesetup.dto.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Service extends Threescale{
	private String name;
	private String servicename;
	private String description;
	private String deploymentoption;
	
	
	
	public Service(String accessToken, String name){
			this.name = name;
			this.accessToken = accessToken;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public void isSelfManaged(boolean isSelfManaged){
		if(isSelfManaged)
			this.deploymentoption ="self_managed";
	}

	public String getDeploymentoption() {
		return deploymentoption;
	}
	public void setDeploymentoption(String deploymentoption) {
		this.deploymentoption = deploymentoption;
	}
	
	@Override
	public List<NameValuePair> getAllParam(){
		 List<NameValuePair> params = super.getAllParam();
		 params.add(new BasicNameValuePair("name", name) ); 
		
		if (servicename!=null)
			params.add(new BasicNameValuePair("servicename", servicename) );
		if (description!=null && description.trim().length() >0)
			params.add(new BasicNameValuePair("description", description) );
		if (deploymentoption!=null )
			params.add(new BasicNameValuePair("deployment_option", deploymentoption) );
		
		return params;
	}
	
}
