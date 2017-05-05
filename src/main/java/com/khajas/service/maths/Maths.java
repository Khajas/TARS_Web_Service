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

package com.khajas.service.maths;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
/**
 * Supports maths skills, it calls maths api registered at
 * Api name: Newton Maths
 * Link: https://newton.now.sh/simplify/{x+y}
 * @author - Anwar
 */
public class Maths extends ApiCall{
	private String query;
	private String response;
	/**
     * Constructor for maths skill
     */
	public Maths(){
		super.addIntent("find ", "maths", "");
		super.addIntent("calculate ", "maths", "");
		super.addIntent("add ", "maths", "");
		super.addIntent("subtract ", "maths", "");
		super.addIntent("divide ", "maths", "");
		super.addIntent("multiply ", "maths", "");
		super.addIntent(" ", "maths", "");
	}

	 /**
     * Constructor for Maths skill using maths expression
     * @param query
     */
	public Maths(String query){
		this();
		this.query=query;
	}
	
	/**
	 * Sets user query(expression)
	 * @param query
	 */
	public void setQuery(String query){
		this.query=query;
	}
	
	 /**
     * Process the request by calling the URL for maths api
     * and prepare the response, it also makes a call to super class-
     * method to get the JSON response. 
     * @param exprn
     * @return 
     */
        @Override
	public String processRequest(String exprn){
		URL url;
		try {
			url = new URL("https://newton.now.sh/simplify/"+query);
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return "";
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return "";
		}
		try {
			String expression=json.getString("expression");
			String result=json.getString("result");
			response=expression+" is "+result;
		} catch (JSONException e) {
			response=e.getMessage();
		}
            return "";
	}
	
	/**
     * Makes a call to request processor( method processRequest())
     * and returns the response appended by the 'append' parameter.
     * @param append
     * @return response
     */
	@Override
	public String serve(String append) {
		this.processRequest("");    // No need for expression
 		return append+response;
	}
}
/////////////////////	END OF SOURCE CODE	//////////////////////////
