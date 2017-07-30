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
package com.khajas.service.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

/**
 * Supports news skills, it calls news api registered as
 * Api key: 3e39b401637643cca15decf66d9bcfb0
 * Doc: https://newsapi.org/sources
 * @author - Anwar
 */

public class NewsApi extends ApiCall{
	private String newslocation="local";
	/**
	 * Constructor for NewsApi
	 */
	public NewsApi(){
		// Indian News
		super.addIntent("headlines of india", "indiannews", "Indian Headlines: ");
		super.addIntent("indian headlines", "indiannews", "Indian Headlines: ");
		super.addIntent("news of india", "indiannews", "Indian Headlines: ");
		super.addIntent("indian news", "indiannews", "Indian Headlines: ");
		super.addIntent("Indian news headlines", "indiannews", "Indian Headlines: ");
		// Technical news
		super.addIntent("technical news headlines", "technicalnews", "Technical Headlines: ");
		super.addIntent("tecnical headlines", "technicalnews", "Technical Headlines: ");
		super.addIntent("technical news", "technicalnews", "Technical Headlines: ");
		// Sports news
		super.addIntent("sports news headlines", "sportsnews", "Sports Headlines: ");
		super.addIntent("sports news", "sportsnews", "Sports Headlines: ");
		super.addIntent("sports headlines", "sportsnews", "Sports Headlines: ");
		// Local news
		super.addIntent("news headlines", "localnews", "Local Headlines: ");
		super.addIntent("headlines", "localnews", "Local Headlines: ");
		super.addIntent("news", "localnews", "Local Headlines: ");
	}

    /**
     * Process the request by calling the URL for currency api
     * and prepare the response, it also makes a call to super class-
     * method to get the JSON response.
     * @return response
     * 		Response after processing the user query.
     */
    @Override
	public String processRequest(String query){
		String response="";
		URL url = null;
		try {
			if(this.newslocation.equals("localnews"))
				url = new URL("https://newsapi.org/v1/articles?"
						+ "source=cnn&sortBy=top&apiKey=3e39b401637643cca15decf66d9bcfb0");
			else if(this.newslocation.equals("sportsnews"))
				url = new URL("https://newsapi.org/v1/articles?"
						+ "source=espn&sortBy=top&apiKey=3e39b401637643cca15decf66d9bcfb0");
			else if(this.newslocation.equals("technicalnews"))
				url = new URL("https://newsapi.org/v1/articles?"
						+ "source=techcrunch&sortBy=top&apiKey=3e39b401637643cca15decf66d9bcfb0");
			else if(this.newslocation.equals("indiannews"))
				url = new URL("https://newsapi.org/v1/articles?"
					+ "source=the-hindu&sortBy=top&apiKey=3e39b401637643cca15decf66d9bcfb0");
		} catch (MalformedURLException e) {
			response=e.getMessage();
                        return response;
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			JSONArray arr=json.getJSONArray("articles");
			System.out.println(json.toString());
			for(int i=0;i<arr.length();++i){
				response+=(arr.getJSONObject(i).getString("title")+"\n");
			}
		} catch (IOException | JSONException e) {
//			response=e.getMessage();
			return "Can't parse JSON!";
		}
		return response;
	}
	
	/**
	 * Set the location, which may used in future
	 * @param location
	 * 		Any valid location in the world
	 */
	public void setNewsLocation(String location){
		this.newslocation=location;
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
		return append+"\n"+this.processRequest(""); // No query needed at this time ""
	}
}
///////////////////////		END OF SOURCE FILE	///////////////////////////////