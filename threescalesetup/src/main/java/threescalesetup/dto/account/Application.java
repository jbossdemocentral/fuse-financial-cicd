package threescalesetup.dto.account;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import threescalesetup.dto.api.Threescale;

public class Application  extends Threescale  {

	String accountid;
	String applicationplanid;
	String name;
	String description;
	 
	public String getApplicationplanid() {
		return applicationplanid;
	}
	public void setApplicationplanid(String applicationplanid) {
		this.applicationplanid = applicationplanid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	 
	@Override
	public List<NameValuePair> getAllParam(){
		 List<NameValuePair> params = super.getAllParam();
		 params.add(new BasicNameValuePair("name", name) ); 
		 params.add(new BasicNameValuePair("plan_id", applicationplanid) ); 
		 params.add(new BasicNameValuePair("description", description) ); 
		
		return params;
	} 
}
