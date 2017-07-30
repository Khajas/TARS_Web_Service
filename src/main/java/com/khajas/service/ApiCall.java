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

package com.khajas.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * The Following abstract class has code that is common to all services
 * It has one abstract method that all the sub-class must implement
 * It has a static member or array of all intents to be shared among all sub classes
 * @author - Anwar
 */
public abstract class ApiCall {
	private static final ArrayList<Intents> INTENTS_ARRAY=new ArrayList<>();		// Collection of all intents from all skills so far
	public static Set<String> intent_words=new HashSet<String>();		// Collection of all words belonging to skills
	public static double fidelity= 0.4;					// fidelity score limit			
	/**
	 * Accepts the URL for an API, call the given url and returns the JSON object
     * @param url
     * 		URL of API that should be called to resolve the user query.
	 * @return JSONObject
	 * 		Returns an JSONObject(which is the result of contacting the given URL)
     * @throws java.io.IOException
     * 		Signals that an I/O exception of some sort has occurred.
     * @throws org.json.JSONException
     * 		Constructs a new runtime exception with the specified detail message.
	 */
	public JSONObject getJsonObject(URL url) throws IOException, JSONException{
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
	        if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream()),"UTF-8"));
		String output, message="";			
		while ((output = br.readLine()) != null) {
			message+=output;				// Prepare the response
		}
		conn.disconnect();
		return new JSONObject(message);
	}

    /**
     * Accepts the API URL, calls the API using the URL provided
     * @param url
     * 		URL of API that should be called to resolve the user query.
	 * @return JSONArray
	 * 		Returns an JSONArray(which is the result of contacting the given URL)
     * @throws java.io.IOException
     * 		Signals that an I/O exception of some sort has occurred.
     * @throws org.json.JSONException
     * 		Constructs a new runtime exception with the specified detail message.
	 */
	public JSONArray getJSONArray(URL url) throws IOException, JSONException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
        	conn.setRequestMethod("GET");
        	conn.setRequestProperty("Accept", "application/json;charset=UTF-8");
        	if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream()),"UTF-8"));
		String output, message="";
		while ((output = br.readLine()) != null) {
			message+=output;
		}
		conn.disconnect();
		return new JSONArray(message);
	}

	/**
	 * Let's the user set the fidelity( 0.0 fidelity value makes all unidentified search on Wikipedia)
     * @param fidelity
     * 		Fidelity ratio, a higher ratio means greater match score is required.
	 */
	@Deprecated
	@SuppressWarnings("static-access")
	public void setFidelity(double fidelity){
		this.fidelity=fidelity;
	}
	
	/**
     * Adds an intent to static intent_words array
     * @param command
     *      A possible command to invoke the skill
     * @param category
     *      Category that given command belongs to
     * @param response
     *      Response that could be appended to the result received from API
     * @return boolean
     *      Returns true if the intent has been added else returns false
     */
	public boolean addIntent(String command, String category, String response){
		for(Intents i: INTENTS_ARRAY){
				if(i.getCommand().equals(command))
					return false; 			// Similar command already exists
		}
		INTENTS_ARRAY.add(new Intents(command.toLowerCase(),category.toLowerCase(), response));
		intent_words.add(command.toLowerCase());
		return true;
	}

	/**
	 * Prints all the commands from all intents of all skills
	 */
	public static void print_commands() {
		System.out.println("Total intents: "+INTENTS_ARRAY.size());
		for(Intents i: INTENTS_ARRAY)
			System.out.println(i.getCommand());
	}
    /**
     * Searches all the intents to match the user query
     * and returns that most matching intent, based on a cut off score 
     * called fidelity.
     * @param command
     *      The command given by user
     * @return Intent
     *      Returns the intent to which the passed (user command) belongs
     */
	public static Intents searchIntent(String command){
		System.out.println("\tLooking for: "+command+" in total intents: "+INTENTS_ARRAY.size());
		command=command.toLowerCase();
		for(Intents i: INTENTS_ARRAY){
//			System.out.println("Looking for : "+command+" current command: "+i.getCommand());
			if(command.contains(i.getCommand())){
				System.out.println("That's a match +++++++++++++++++++++++++++++++++++");
				return i;
			}
		}
		return null;
	}
        
	/**
     * Abstract method that should be implemented by all the inheriting classes
     * @param append
     *      String that should be appended to response
     * @return response
     *      Response tailored after parsing the JSON from API
     */
	public abstract String serve(String append);
        
    /**
     * Process user request and returns response
     * @param query
     * 		User query.
     * @return 
     * 		Returns response after processing the user request
     */
    public abstract String processRequest(String query);
}
//////////////////////////	END OF SOURCE FILE	///////////////////////////////////////