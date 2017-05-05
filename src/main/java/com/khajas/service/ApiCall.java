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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
	 * @return JSONObject
         * @throws java.io.IOException
         * @throws org.json.JSONException
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
         * @return JSONArray
         * @throws java.io.IOException
         * @throws org.json.JSONException
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
         * @param f
	 */
	public void setFidelity(double f){
		fidelity=f;
	}
	
	/**
	 * Adds a new intent to static intents collections, that is shared among all skills
	 * @param command
	 * @param category
	 * @param response
	 * @return add_status
	 */
	public boolean addIntent(String command, String category, String response){
//		if(!isValidIntent(command, category)) return false;	// It's an invalid intent
		for(Intents i: INTENTS_ARRAY){
				if(i.getCommand().equals(command))
					return false; 			// Similar command already exists
		}
		INTENTS_ARRAY.add(new Intents(command.toLowerCase(),category.toLowerCase(), response));
		String[] allwords=command.toLowerCase().split(" ");
		for(String s: allwords){
			intent_words.add(s);
		}
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
	 * Takes user command and find the match score of all the intents
	 * and returns the intent with highest score
	 * @param command
	 * @return intent
	 */
	public static Intents searchIntent(String command){
		Map<Intents, Double> intentScore=new HashMap<>();
		double highscore=-10000;
		System.out.println("\tLooking for: "+command+" in total intents: "+INTENTS_ARRAY.size());
		for(Intents i: INTENTS_ARRAY){
			double score=StringSimilarity.similarity(i.getCommand().toLowerCase(),
					command.toLowerCase());					// Find the matching score
			System.out.println("Intent: "+i.getCommand()+" score: "+score);
			if(score>highscore) highscore=score;
			intentScore.put(i, score);						// Put the intent and it's score
		}
		for(Entry<Intents,Double> e: intentScore.entrySet()){
			if((e.getValue()==highscore) && (highscore>=fidelity))			// Find the intent with high score
				return e.getKey();						// and if it's above the fidelity
		}
		return null;
	}
        // As of now there is no need for validating an intent
	// This may be added when user could be able to add their apis
	/*
	private boolean isValidIntent(String command, String category){
		WordRecommendations wr_cm=new WordRecommendations(command);
		WordRecommendations wr_ct=new WordRecommendations(command);
		if(wr_cm.isValidEnglishWord() && wr_ct.isValidEnglishWord())
			return true;
		return false;
	}
	*/
        
        /**
	 * Returns the response after processing the response received from API
	 * by appending the response with 'append' parameter.
	 * @param append
	 * @return response
	 */
	public abstract String serve(String append);
        
        /**
         * Process user request and returns response
         * @param query
         * @return 
         */
        public abstract String processRequest(String query);
}
//////////////////////////	END OF SOURCE FILE	///////////////////////////////////////
