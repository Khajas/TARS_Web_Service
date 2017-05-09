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
package com.khajas.service.cricscore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

/**
 * Supports cricket scores skills, it calls cricscore api from
 * http://cricscore-api.appspot.com
 * @author - Anwar
 */
public class CricApi extends ApiCall{
	private String response;
	private final ArrayList<String> teams;
	/**
     * Constructor for cricket scores api
     */
	@SuppressWarnings("serial")
	public CricApi(){
		super.addIntent("ipl score ", "iplscore", "");
		super.addIntent("ipl ", "iplscore", "");
		super.addIntent("Indian Premier League ", "iplscore", "");
		super.addIntent("t20 score ", "iplscore", "");
		super.addIntent("t20 ", "iplscore", "");
		teams=new ArrayList<String>(){
				{	add("Delhi Daredevils");
				 	add("Gujarat Lions");
				 	add("Kings XI Punjab");
				 	add("Kolkata Knight Riders");
				 	add("Mumbai Indians");
				 	add("Rising Pune Supergiant");
				 	add("Royal Challengers Bangalore");
				 	add("Sunrisers Hyderabad");
				}};
	}
	
	/**
        *  Gets all Cricket Scores by calling the URL for currency api
        * and prepare the response, it also makes a call to super class-
        * method to get the JSON response.
        * @return 
        */
        @Override
	public String processRequest(String query){
		response="testing";
		URL url;
		long ipl_id=-1;
		try {
			url = new URL("http://cricscore-api.appspot.com/csa");
			JSONArray json=super.getJSONArray(url);
			for(int i=0;i<json.length();++i){
				JSONObject match_obj=json.getJSONObject(i);
				String team2=match_obj.getString("t2");
				String team1=match_obj.getString("t1");
				if((teams.contains(team1)) &&
						teams.contains(team2)){
					ipl_id=match_obj.getLong("id");
				}
			}
			if(ipl_id!=-1){
				url = new URL("http://cricscore-api.appspot.com/csa?id="+ipl_id);
				JSONArray match_score = super.getJSONArray(url);
				response=match_score.toString();
				JSONObject match_details=match_score.getJSONObject(0);
				String match_de=match_details.getString("de")+"\n";
				match_de=match_de.replaceAll(": ", ":\n");
				String match_si=match_details.getString("si");
				if(match_de.contains(match_si))
					match_si="";
				response=match_de+match_si;
			}
			else response="There are no ipl matches today!";
		} catch (MalformedURLException e) {
			response=e.getMessage();
		} catch (IOException | JSONException e) {
			response=e.getMessage();
		}
                return response;
	}
	 /**
     * Makes a call to request processor( method processRequest())
     * and returns the response appended by the 'append' parameter.
     * @param append
     * @return response
     */
	@Override
	public String serve(String append) {
		this.processRequest("");  // There is no need for user query
		return response;
	}
}
///////////////////////         END OF SOURCE FILE      //////////////////////////