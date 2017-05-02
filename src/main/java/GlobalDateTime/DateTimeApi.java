package GlobalDateTime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
import com.khajas.service.googlemap.GetCityLocation;
//	 App/username khajas
// 	 registered to ali.sid697@gmail.com
//
public class DateTimeApi extends ApiCall{
	private String response;
	private String city;
	public DateTimeApi(){
		super.addIntent("time", "datetime", "");
		super.addIntent("time now", "datetime", "");
		super.addIntent("what's time", "datetime", "");
		super.addIntent("date", "datetime", "");
		super.addIntent("today's date", "datetime", "");
		super.addIntent("local time", "datetime", "");
		super.addIntent("time of ", "datetime", "");
		super.addIntent("time in ", "datetime", "");
	}
	public DateTimeApi(String city){
		this();
		this.city=city;
	}
	public void processRequest(){
		URL url = null;
		try {
			GetCityLocation gcl=new GetCityLocation(this.city);
			System.out.println(gcl.serve(""));
			url = new URL("http://api.geonames.org/timezoneJSON?"
					+ "lat="+gcl.getLat()+"&lng="+gcl.getLng()+"&username=khajas");
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return;
		}
		JSONObject json = null;
		try {
			json = super.callApi(url);
			response=json.getString("time");
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return;
		}
	}
	@Override
	public String serve(String append) {
		this.processRequest();
		return this.getResponse();
	}
	// Getters & Setters
	public void setCity(String city){
		this.city=city;
	}
	public String getResponse(){
		if(response!=null){
			return response;
		}
		if(city.isEmpty())
			return "No local date or time information";
		else return "No date or time info for:"+city;
	}
}
