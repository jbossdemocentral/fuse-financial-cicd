package threescalesetup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class ThreescaleManagment {

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

		System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());

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
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);


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

		System.out.println("\nSending 'DELETE' request to URL : " + url);
		System.out.println("Post parameters : " + deleteParams);
		System.out.println("Response Code : " + responseCode);

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
