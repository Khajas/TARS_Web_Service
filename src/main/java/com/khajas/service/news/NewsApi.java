package com.khajas.service.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

// The new api
// Api key: 3e39b401637643cca15decf66d9bcfb0
// Doc: https://newsapi.org/sources

public class NewsApi extends ApiCall{
	private String newslocation="local";
	public NewsApi(){
		// Local news
		super.addIntent("what's local news ", "localnews", "Local Headlines: ");
		super.addIntent("what's today's news ", "localnews", "Local Headlines: ");
		super.addIntent("what's local headlines ", "localnews", "Local Headlines: ");
		super.addIntent("what's today's headlines ", "localnews", "Local Headlines: ");
		super.addIntent("headlines ", "localnews", "Local Headlines: ");
		super.addIntent("news ", "localnews", "Local Headlines: ");
		// Indian News
		super.addIntent("what's indian news", "indiannews", "Indian Headlines: ");
		super.addIntent("what's indian news headlines", "indiannews", "Indian Headlines: ");
		super.addIntent("what's news of india", "indiannews", "Indian Headlines: ");
		super.addIntent("what's today's news of india", "indiannews", "Indian Headlines: ");
		super.addIntent("what's headlines at indian ", "indiannews", "Indian Headlines: ");
		super.addIntent("what's today's indian headlines ", "indiannews", "Indian Headlines: ");
		super.addIntent("headlines of india", "indiannews", "Indian Headlines: ");
		super.addIntent("indian headlines", "indiannews", "Indian Headlines: ");
		super.addIntent("news of india", "indiannews", "Indian Headlines: ");
		// Technical news
		super.addIntent("what's technical news ", "technicalnews", "Technical Headlines: ");
		super.addIntent("what's today's technical news ", "technicalnews", "Technical Headlines: ");
		super.addIntent("what's technical headlines ", "technicalnews", "Technical Headlines: ");
		super.addIntent("what's today's technical headlines ", "technicalnews", "Technical Headlines: ");
		super.addIntent("headlines technical ", "technicalnews", "Technical Headlines: ");
		super.addIntent("news technical ", "technicalnews", "Technical Headlines: ");
		// Sports news
		super.addIntent("what's sports news ", "sportsnews", "Sports Headlines: ");
		super.addIntent("what's today's sports news ", "sportsnews", "Sports Headlines: ");
		super.addIntent("what's sports headlines ", "sportsnews", "Sports Headlines: ");
		super.addIntent("what's today's sports headlines ", "sportsnews", "Sports Headlines: ");
		super.addIntent("headlines sports ", "sportsnews", "Sports Headlines: ");
		super.addIntent("news sports ", "sportsnews", "Sports Headlines: ");
	}

	private String processRequest(){
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
			return "";
		}
		JSONObject json = null;
		try {
			json = super.callApi(url);
			JSONArray arr=json.getJSONArray("articles");
			System.out.println(json.toString());
			for(int i=0;i<arr.length();++i){
				response+=(arr.getJSONObject(i).getString("title")+"\n");
			}
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return "Can't parse JSON";
		}
		return response;
	}
	
	public void setNewsLocation(String loc){
		this.newslocation=loc;
	}
	
	@Override
	public String serve(String append) {
		return append+"\n"+this.processRequest();
	}
}
