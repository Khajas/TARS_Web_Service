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
package com.khajas.service.weatherinfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
 /**
 * Supports Weather skills, it calls weather forecast API
 * Api documentation can be found on http://openweathermap.org/forecast16
 * Api key: 4cc5cf846711019e80d71b42dcd40434
 * @author - Anwar
 */
public class WeatherInfo extends ApiCall{
	private String weatherDesc;
	private String averageTemp;
	private String response;
	private String city;
	private String zipcode;
	private String countrycode;
	/**
     * Constructor for weatherinfo skill
     */
	public WeatherInfo(){
        	// Local Weather information
	        super.addIntent("Weather forecast", "weatherinfo", "It's seems to be ");
	        super.addIntent("weather", "weatherinfo", "It's ");
	        super.addIntent("forecast", "weatherinfo", "It's ");
	}
	 /**
     * Constructor for weatherinfo using city other than user default(client IP's city)
     * @param city
     * 		User city
     */
	public WeatherInfo(String city){
		this();
		this.city=city;
	}
	 /**
     * Constructor for wetherinfo for zipcode, countrycode other than user default(client IP's city)
     * @param zipcode
     * 		Zipcode of user/client.
     * @param countrycode
     * 		Countrycode of user/client.
     */
	public WeatherInfo(String zipcode, String countrycode){
		this();
		this.zipcode=zipcode;
		this.countrycode=countrycode;
	}
	/**
     * Process the request by calling the URL for weatherinfo api
     * and prepare the response, it also makes a call to super class-
     * method to get the JSON response. 
     * @return response
     * 		Response for the given query.
     */
        @Override
	public String processRequest(String query){		
		URL url;
		try {
			if((zipcode!=null) && (countrycode!=null))
				url = new URL("http://api.openweathermap.org/data/2.5/weather?"
						+ "zip="+zipcode+","+countrycode+"&appid=4cc5cf846711019e80d71b42dcd40434");
			else
			url = new URL("http://api.openweathermap.org/data/2.5/weather?"
					+ "q="+city+"&appid=4cc5cf846711019e80d71b42dcd40434");
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			return e.getMessage();
		}
		try {
			weatherDesc = json.getJSONArray("weather").
                                getJSONObject(0).getString("description");
			double temp = Double.parseDouble(json.getJSONObject("main").
                                getString("temp")) - 273.15 ;
			 averageTemp = (String) String.format("%.2f", temp);
		} catch (JSONException e) {
			return e.getMessage();
		}
		response=weatherDesc+" in "+city+" now and temperature is "+this.averageTemp+"'C";
                return response;
	}
	/**
     * Makes a call to request processor( method processRequest())
     * and returns the response appended by the 'append' parameter.
     * @param append
     * 		A customized string that could be appended to response.
     * @return response
     * 		Response for the given query.
     */
	@Override
	public String serve(String append){
		this.processRequest("");
		return append+response;
	}
		
	// GETTERS FOR ALL PARAMETERS
	/**
	 * Returns the weather information
	 * @return weatherinfo
	 * 		Gives the weather information
	 */
	public String getWeatherInfo(){
		if(this.response!=null)
			return this.response;
		else return "No weather information found!";
	}
	
	/**
	 * Set the city 
	 * @param city
	 * 		Any valid city in the world.
	 */
	public void setCity(String city) {
		System.out.println("Setting city: "+city);
		this.city=city;
	}
}
///////////////////////		END OF SOURCE FILE	////////////////////////////////