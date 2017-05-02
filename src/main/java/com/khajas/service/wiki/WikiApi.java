package com.khajas.service.wiki;

import java.util.Iterator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

public class WikiApi extends ApiCall{
	private String query;
	public boolean status=true;
	public WikiApi(){
		super.addIntent("What's ", "wikiapi", "");
		super.addIntent("What's a ", "wikiapi", "");
		super.addIntent("What is ", "wikiapi", "");
		super.addIntent("What is a ", "wikiapi", "");
		super.addIntent("What do you mean by ", "wikiapi", "");
		super.addIntent("means ", "wikiapi", "");
		super.addIntent("Who is ", "wikiapi", "");
		//Exceptional Intent, adding "of" eg: Who is the president of United States
		super.addIntent("Who is of ", "wikiapi", "");
		super.addIntent("Who is the ", "wikiapi", "");
	}
	public WikiApi(String query){
		this();
		this.query=query;
	}
	public String processRequest(String query){
		System.out.println("Processing request for :"+query);
	try {
		query=URLEncoder.encode(query,"UTF-8");
        URL url = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query"
        		+ "&prop=extracts&exintro=&redirects=true&explaintext=&titles="+query);
		JSONObject json;
		try {
			json = super.callApi(url);
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
	@Override
	public String serve(String append){
			return this.processRequest(this.query);
	}
	public void setQuery(String query) {
		this.query=query;
	}
}
