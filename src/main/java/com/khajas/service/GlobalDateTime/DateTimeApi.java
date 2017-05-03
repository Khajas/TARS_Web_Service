package com.khajas.service.GlobalDateTime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
	private String timeZoneId;
	private String country;
	public DateTimeApi(){
		super.addIntent("time ", "datetime", "");
		super.addIntent("can you give me ", "datetime", "");
		super.addIntent("can I get ", "datetime", "");
		super.addIntent("give time ", "datetime", "");
		super.addIntent("show me time ", "datetime", "");
		super.addIntent("may I know ", "datetime", "");
		super.addIntent("current time ", "datetime", "");
		super.addIntent("time now", "datetime", "");
		super.addIntent("what's time", "datetime", "");
		super.addIntent("date", "datetime", "");
		super.addIntent("today's date", "datetime", "");
		super.addIntent("local time", "datetime", "");
		super.addIntent("time of ", "datetime", "");
		super.addIntent("time in ", "datetime", "");
		super.addIntent("current time in ", "datetime", "");
		super.addIntent("may I know the current time in ", "datetime", "");
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
			String time=json.getString("time");
			String tm=getTime(time);
			String dt=getDate(time);
			response="It's "+tm+"("+dt+")"+" in "+city.trim()+", "+
			json.getString("countryCode")+" (Time Zone: "+json.getString("timezoneId")+")";
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return;
		}
	}
	
	private String getDate(String time){
		DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		return dateTime.format(f);
	}
	
	private String getTime(String time){
		DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm a");
		return dateTime.format(f);
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
