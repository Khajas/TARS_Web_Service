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
	        super.addIntent("What's today's forecast ", "weatherinfo", "It's seems to be ");
	        super.addIntent("What's today's weather ", "weatherinfo", "It's seems to be ");
	        super.addIntent("Today's forecast ", "weatherinfo", "It's seems to be ");
	        super.addIntent("Weather info ", "weatherinfo", "It's seems to be ");
	        super.addIntent("weather ", "weatherinfo", "It's ");
	        super.addIntent("forecast ", "weatherinfo", "It's ");
	        // Remote Weather information
	        super.addIntent("What's today's forecast in ", "weatherinforemote", "It's ");
	        super.addIntent("What's today's weather in ", "weatherinforemote", "It's ");
	        super.addIntent("What's today's forecast of ", "weatherinforemote", "It's ");
	        super.addIntent("What's today's weather of ", "weatherinforemote", "It's ");
	        super.addIntent("How is weather in ", "weatherinforemote", "It's ");
	        super.addIntent("weather in ", "weatherinforemote", "It's ");
	        super.addIntent("weather of ", "weatherinforemote", "It's ");
	        super.addIntent("forecast of ", "weatherinforemote", "It's ");
	
	        super.addIntent("What's today's temperature in ", "weatherinforemote", "It's ");
	       super.addIntent("What's today's temperature of ", "weatherinforemote", "It's ");
	       super.addIntent("How is temperature in ", "weatherinforemote", "It's ");
	       super.addIntent("temperature in ", "weatherinforemote", "It's ");
	       super.addIntent("temperature of ", "weatherinforemote", "It's ");
	}
	
	 /**
     * Constructor for weatherinfo using city other than user default(client IP's city)
     * @param city
     */
	public WeatherInfo(String city){
		this();
		this.city=city;
	}

	 /**
     * Constructor for wetherinfo for zipcode, countrycode other than user default(client IP's city)
     * @param zipcode
     * @param countrycode
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
     * @return response
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
	 */
	public String getWeatherInfo(){
		if(this.response!=null)
			return this.response;
		else return "No weather information found!";
	}
	
	/**
	 * Set the city 
	 * @param city
	 */
	public void setCity(String city) {
		System.out.println("Setting city: "+city);
		this.city=city;
	}
}
///////////////////////		END OF SOURCE FILE	////////////////////////////////
/** sample response
 *{
    "coord": {
        "lon": -88.75,
        "lat": 41.93
    },
    "weather": [
        {
            "id": 800,
            "main": "Clear",
            "description": "clear sky",
            "icon": "01n"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 283.52,
        "pressure": 1021,
        "humidity": 62,
        "temp_min": 280.15,
        "temp_max": 286.15
    },
    "visibility": 16093,
    "wind": {
        "speed": 4.1,
        "deg": 50
    },
    "clouds": {
        "all": 1
    },
    "dt": 1492481700,
    "sys": {
        "type": 1,
        "id": 963,
        "message": 0.0063,
        "country": "US",
        "sunrise": 1492513760,
        "sunset": 1492562389
    },
    "id": 4889553,
    "name": "DeKalb",
    "cod": 200
}
*/
