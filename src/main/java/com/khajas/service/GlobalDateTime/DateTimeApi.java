/*
 * Copyright (c) 2017 Anwar.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anwar - initial API and implementation and/or initial documentation
 */
package com.khajas.service.globaldatetime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
import com.khajas.service.googlemap.GetCityLocation;

/**
 * Supports global date and time skills, it calls datetime api registered as
 * App/username 'khajas'
 *   Registered to ali.sid697@gmail.com 
 *   @author - Anwar
 */
public class DateTimeApi extends ApiCall{
	private String response;
	private String city;
	@SuppressWarnings("unused")
	private String timeZoneId;
	@SuppressWarnings("unused")
	private String country;
	 
	/**
     * Constructor for datetime skill
     */
	public DateTimeApi(){
		super.addIntent("current time", "datetime", "");
		super.addIntent("time now", "datetime", "");
		super.addIntent("today's date", "datetime", "");
		super.addIntent("local time", "datetime", "");
		super.addIntent("time", "datetime", "");
		super.addIntent("date", "datetime", "");
	}
	 
	/**
     * Constructor for city other than user local city(IP location)
     * @param city
     * 		City from which client is contacting TARS server
     */
	public DateTimeApi(String city){
		this();
		this.city=city;
	}
	
    /**
     * Process the request by calling the URL for geonames API
     * and prepare the response, it also makes a call to super class-
     * method to get the JSON response.
     * @return response
     * 		Response after processing the user request
     */
        @Override
	public String processRequest(String query){
		URL url;
		try {
			GetCityLocation gcl=new GetCityLocation(this.city);
			System.out.println(gcl.serve(""));
			url = new URL("http://api.geonames.org/timezoneJSON?"
					+ "lat="+gcl.getLat()+"&lng="+gcl.getLng()+"&username=khajas");
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return response;
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			String time=json.getString("time");
			String tm=getTime(time);
			String dt=getDate(time);
			response="It's "+tm+"("+dt+")"+" in "+city.trim()+", "+
			json.getString("countryCode")+" (Time Zone: "+json.getString("timezoneId")+")";
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			response=e.getMessage();
		}
        return response;
	}
	
	/**
	 * Returns the date by parsing the datetime parameter
	 * @param date_time
	 * 		Accepts DateTime string for parsing to only date
	 * @return date
	 * 		Date after parsing the input string(that represents dateTime)
	 */
	private String getDate(String date_time){
		DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date_time, formatter);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		return dateTime.format(f);
	}
	
	/**
     * Returns the time(hh:mm a) by parsing the datetime parameter
     * @param date_time
	 * 		Accepts DateTime string for parsing to only time
	 * @return time
	 * 		time after parsing the input string(that represents dateTime)
	 */
	private String getTime(String date_time){
		DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date_time, formatter);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm a");
		return dateTime.format(f);
	}
	
	/**
     * Makes a call to request processor( method processRequest())
     * and returns the response appended by the 'append' parameter.
     * @param append
     * 		A customized string that should be appended to response.
     * @return response
     * 		Response after processing request from user.
     */
	@Override
	public String serve(String append) {
		this.processRequest("");
		return this.getResponse();
	}

	/**
    * Sets the city
    * @param city
    * 		Any valid city in the world.
    */
	public void setCity(String city){
		this.city=city;
	}
	
	/**
    * Returns the prepared response
    * @return response
    * 		Response after processig user query
    */
	public String getResponse(){
		if(response!=null){
			return response;
		}
		if(city.isEmpty())
			return "No local date or time information";
		else return "No date or time info for:"+city;
	}
}
//////////////////////////	END OF SOURCE FILE	///////////////////////////////