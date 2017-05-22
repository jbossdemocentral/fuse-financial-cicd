package threescalesetup.dto;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicNameValuePair;

public class Limit extends Threescale {

	public static final String PERIOD_MINUTE = "minute";
	public static final String PERIOD_HOUR = "hour";
	public static final String PERIOD_DAY = "day";
	public static final String PERIOD_WEEK = "week";
	public static final String PERIOD_MONTH = "month";
	public static final String PERIOD_YEAR = "year";
	public static final String PERIOD_ETERNITY = "eternity";
	
	String period;
	String value;
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public List<NameValuePair> getAllParam(){
		 List<NameValuePair> params = super.getAllParam();
		 params.add(new BasicNameValuePair("period", period) ); 
		 params.add(new BasicNameValuePair("value", value) ); 
		 
		return params;
	}
	
	
	
	
}
