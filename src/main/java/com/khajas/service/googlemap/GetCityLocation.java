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
package com.khajas.service.googlemap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

/**
 * Supports location services, it calls location API registered at
 * @Google Maps
 * @author - Anwar
 */
public class GetCityLocation extends ApiCall{
	
	private String latitude;
	private String longitude;
	private final String city;
	private String response;
	
        /**
         * Constructor for 'city' other than user's current location
         * @param city
         */
	public GetCityLocation(String city){
		this.city=city;
	}
	
        /**
         * Process the request by calling the URL for google maps API
         * and prepare the response, it also makes a call to super class
         * method to get the JSON response.
         * @return 
         */
        @Override
	public String processRequest(String query){
		URL url;
		try {
			String userCity=URLEncoder.encode(this.city,"UTF-8");
			url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="+userCity+"&sensor=false");
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			response=e.getMessage();
			return response;
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			latitude=json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").
					getJSONObject("location").getString("lat");
			longitude=json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").
					getJSONObject("location").getString("lng");
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return response;
		}
		response="Lat: "+latitude+" lng: "+longitude+"city: "+city;
                return response;
	}

	/**
	 * Returns the latitude for the assigned city
	 * @return latitude
	 */
	public String getLat(){
		return latitude;
	}

	/**
        * Returns the longitude for the assigned city
        * @return longitude
        */
	public String getLng(){
		return longitude;
	}

        /**
         * Makes a call to request processor( method processRequest())
         * and returns the response appended by the 'append' parameter.
         * @param append
         * @return response
         */
	@Override
	public String serve(String append) {
		return this.processRequest(""); // No query input needed
	}
}
/////////////////////	END OF SOURCE FILE	////////////////////////////////////////
