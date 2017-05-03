package com.khajas.service.cricscore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

public class CricApi extends ApiCall{
	private String response;
	private ArrayList<String> teams;
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
	
	public void getAllCricScores(){
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
		} catch (IOException e) {
			response=e.getMessage();
		} catch (JSONException e) {
			response=e.getMessage();
		}
	}
	
	@Override
	public String serve(String append) {
		this.getAllCricScores();
		return response;
	}
}
