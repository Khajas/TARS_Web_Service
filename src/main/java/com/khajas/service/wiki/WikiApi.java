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
package com.khajas.service.wiki;

import java.util.Iterator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
/**
 * Supports quering the wikipedia, it calls wikipedia API
 * @author - Anwar
 */

public class WikiApi extends ApiCall{
	private String query;
	public boolean status=true;
	
	/**
     * Constructor for Wikipedia querying skill
     */
	public WikiApi(){
		super.addIntent("What do you mean by", "wikiapi", "");
	}

	/**
     * Constructor for wikipedia querying skill
	 * @param query
	 * 		User query
     */
	public WikiApi(String query){
		this();
		this.query=query;
	}

	/**
     * Process the request by calling the URL for currency API
     * and prepare the response, it also makes a call to super class-
     * method to get the JSON response.
     * @param query
     * 		User query.
     * @return response
     * 		Response for the given query.
     */
	public String processRequest(String query){
		System.out.println("Processing request for :"+query);
	try {
		query=URLEncoder.encode(query,"UTF-8");
        URL url = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query"
        		+ "&prop=extracts&exintro=&redirects=true&explaintext=&titles="+query);
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			String str="";
			json=json.getJSONObject("query").getJSONObject("pages");
			Iterator<?> itr=json.keys();
			while(itr.hasNext()) {
			    JSONObject name = (JSONObject) json.get((String) itr.next());
			    str=name.getString("extract");
			}
    		str=str.replace(".\\n", ".\r\n");
                str=str.replace("\\n", "\r\n");
    		System.out.println(" String searched: "+str);
    		return str;
		}catch (JSONException e) {
			status=false;
			return "I don't understand that for now!";
		}
	  } catch (MalformedURLException e) {
		  status=false;
		  return "Check your request!";
	  } catch (IOException e) {
		  status=false;
		  return "You may check your request!";
	  }
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
			return this.processRequest(this.query);
	}
	/**
	 * Sets the query
	 * @param query
	 * 		User query.
	 */
	public void setQuery(String query) {
		this.query=query;
	}
}
/////////////////////	END OF SOURCE FILE	////////////////////////////////////