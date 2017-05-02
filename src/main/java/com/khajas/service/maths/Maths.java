package com.khajas.service.maths;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

// Api name: Newton Maths
// Link: https://newton.now.sh/simplify/{x+y}

public class Maths extends ApiCall{
	private String query;
	private String response;
	public Maths(){
		super.addIntent("find ", "maths", "");
		super.addIntent("calculate ", "maths", "");
		super.addIntent("add ", "maths", "");
		super.addIntent("subtract ", "maths", "");
		super.addIntent("divide ", "maths", "");
		super.addIntent("multiply ", "maths", "");
		super.addIntent(" ", "maths", "");
	}
	public Maths(String query){
		this();
		this.query=query;
	}
	
	public void setQuery(String query){
		this.query=query;
	}
	
	public void calculate(){
	
		URL url = null;
		try {
			url = new URL("https://newton.now.sh/simplify/"+query);
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return;
		}
		JSONObject json = null;
		try {
			json = super.callApi(url);
			System.out.println(json.toString());
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return;
		}
		try {
			String expression=json.getString("expression");
			String result=json.getString("result");
			response=expression+" is "+result;
		} catch (JSONException e) {
			response=e.getMessage();
			return;
		}
	}
	
	@Override
	public String serve(String append) {
		this.calculate();
		return append+response;
	}

}
