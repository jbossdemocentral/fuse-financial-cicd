package threescalesetup.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ApplicationPlan extends Threescale {
	
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public List<NameValuePair> getAllParam(){
		 List<NameValuePair> params = super.getAllParam();
		 params.add(new BasicNameValuePair("name", name) ); 
		
		 //setup_fee":0.0,"cost_per_month":0.0,"trial_period_days":0,"cancellation_period":0
		return params;
	}

}
