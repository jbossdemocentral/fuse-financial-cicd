package threescalesetup.operation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import threescalesetup.operation.api.MetricsOperation;

public class ThreescaleManagment {
	
	Logger log = Logger.getLogger(ThreescaleManagment.class.getName());

	private String hostName;
	private HttpClient client = HttpClientBuilder.create().build();
	
	///admin/api/services.xml
	// HTTP POST request
	
	public ThreescaleManagment(String hostName){
			this.hostName=hostName;
	}
	
	public String sendGet(String apiUrl)throws Exception{
		String url = "https://"+hostName+"-admin.3scale.net/"+apiUrl;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		

		HttpResponse response = client.execute(request);

		log.log(Level.FINE, "Response Code : {0}", response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
		
	public String sendPost(String apiUrl, List<NameValuePair> postParams) throws Exception {

			String url = "https://"+hostName+"-admin.3scale.net/"+apiUrl;
			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setHeader("cache-control", "no-cache");
			
			post.setEntity(new UrlEncodedFormEntity(postParams));

			HttpResponse response = client.execute(post);

			int responseCode = response.getStatusLine().getStatusCode();
			
			
			log.log(Level.FINE, "\nSending 'POST' request to URL : {0}", url);
			log.log(Level.FINE, "Post parameters : [{0}]", postParams);
			log.log(Level.FINE, "Response Code : [{0}]", responseCode);
			


			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			if(responseCode != 201){
				throw new Exception("ERROR from 3scale : ["+responseCode+"] -> " +  result.toString());
			}
			return result.toString();

	}
	
	public String sendDelete(String apiUrl, List<NameValuePair> deleteParams) throws Exception {

		String url = "https://"+hostName+"-admin.3scale.net/"+apiUrl;
		
		HttpEntityEnclosingRequestBase delete = new HttpPost(url){			
			 @Override
			    public String getMethod() {
			        return "DELETE";
			    }
		};

		// add header
		delete.setHeader("Content-Type", "application/x-www-form-urlencoded");
		delete.setHeader("cache-control", "no-cache");
		
		delete.setEntity(new UrlEncodedFormEntity(deleteParams));

		HttpResponse response = client.execute(delete);

		int responseCode = response.getStatusLine().getStatusCode();

		log.log(Level.FINE, "\nSending 'DELETE' request to URL : {0}", url);
		log.log(Level.FINE, "DELETE parameters : [{0}]", deleteParams);
		log.log(Level.FINE, "Response Code : [{0}]", responseCode);

		BufferedReader rd = new BufferedReader(
	                new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		if(responseCode != 200){
			throw new Exception("ERROR from 3scale : ["+responseCode+"] -> " +  result.toString());
		}
		return result.toString();
		
	}
}
