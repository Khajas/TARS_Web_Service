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

public abstract class ApiCall {
	private static ArrayList<Intents> intents=new ArrayList<>();
	public static Set<String> intent_words=new HashSet<String>();
	public static double fidality= 0.4;

	public JSONObject callApi(URL url) throws IOException, JSONException{
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
		return new JSONObject(message);
	}
	
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
	
	
	public void setFidality(double f){
		fidality=f;
	}
	
	public boolean addIntent(String command, String category, String response){
//		if(!isValidIntent(command, category)) return false;	// It's an invalid intent
		for(Intents i: intents){
				if(i.getCommand().equals(command))
					return false; // Similar command already exists
		}
		intents.add(new Intents(command.toLowerCase(),category.toLowerCase(), response));
		String[] allwords=command.toLowerCase().split(" ");
		for(String s: allwords){
			intent_words.add(s);
		}
		return true;
	}
	public abstract String serve(String append);

	public static void print_commands() {
		System.out.println("Total intents: "+intents.size());
		for(Intents i: intents)
			System.out.println(i.getCommand());
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

	public static Intents searchIntent(String command){
		Map<Intents, Double> intentScore=new HashMap<>();
		double highscore=-10000;
		System.out.println("\tLooking for: "+command+" in total intents: "+intents.size());
		for(Intents i: intents){
			double score=StringSimilarity.similarity(i.getCommand().toLowerCase(),
					command.toLowerCase());
			System.out.println("Intent: "+i.getCommand()+" score: "+score);
			if(score>highscore) highscore=score;
			intentScore.put(i, score);
		}
		for(Entry<Intents,Double> e: intentScore.entrySet()){
			if((e.getValue()==highscore) && (highscore>=fidality))
				return e.getKey();
		}
		return null;
	}
}