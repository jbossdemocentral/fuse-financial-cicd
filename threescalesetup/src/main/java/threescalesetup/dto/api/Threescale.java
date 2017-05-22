package threescalesetup.dto.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicNameValuePair;

public class Threescale {
	
	protected String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public List<NameValuePair> getAllParam(){
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("access_token", accessToken) );
		 
		return params;
	}
	
	
	public String getQuery(){
		 StringBuffer sb = new StringBuffer();
		 sb.append("?access_token="+accessToken);
		return sb.toString();
	}

}
