package com.khajas.service.googlemap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

public class GetCityLocation extends ApiCall{
	private String lat;
	private String lng;
	private String city;
	private String response;
	public GetCityLocation(String city){
		this.city=city;
	}
	public void processRequest(){
		URL url = null;
		try {
			String city=URLEncoder.encode(this.city,"UTF-8");
			url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+city+"&sensor=false");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			response=e.getMessage();
			return;
		}
		JSONObject json = null;
		try {
			json = super.callApi(url);
			lat=json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").
					getJSONObject("location").getString("lat");
			lng=json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").
					getJSONObject("location").getString("lng");
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return;
		}
		response="Lat: "+lat+" lng: "+lng+"city: "+city;
	}
	
	public String getLat(){
		return lat;
	}
	public String getLng(){
		return lng;
	}
	@Override
	public String serve(String append) {
		this.processRequest();
		return response;
	}
}
