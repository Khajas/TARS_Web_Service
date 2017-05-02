package com.khajas.service.currency;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;

public class CurrencyApi extends ApiCall{
	private String base="USD";
	private String response;
	private CurrencyApi(){
		super.addIntent("What's a dollar worth ", "currenyapi", "$1: ");
		super.addIntent("dollar rate ", "currenyapi", "1 USD: ");
		super.addIntent("1$ ", "currenyapi", "1 USD: ");
		super.addIntent("Exchange rates ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("Today's exchange rate ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("What is today's exchange rate ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("Today's currency rate ", "currenyapi", "Currency Rate(1 USD): ");
		super.addIntent("What is today's currency rate ", "currenyapi", "Currency Rate(1 USD): ");
	}
	
	public CurrencyApi(String base){
		this();
		this.base=base;
	}
	
	
	private void processRequest(){
		response="Sorry, I can't fetch currency details!";
		URL url = null;
		try {
				url = new URL("http://api.fixer.io/latest?base="+base);
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return;
		}
		JSONObject json = null;
		try {
			json = super.callApi(url);
			double EUR=json.getJSONObject("rates").getDouble("EUR");
			double GBP=json.getJSONObject("rates").getDouble("GBP");
			double CAD=json.getJSONObject("rates").getDouble("CAD");
			double INR=json.getJSONObject("rates").getDouble("INR");
			double AUD=json.getJSONObject("rates").getDouble("AUD");
			response="\nEUR: "+EUR
					+"\nGBP: "+GBP
					+"\nCAD: "+CAD
					+"\nINR: "+INR
					+"\nAUD: "+AUD;
		} catch (IOException | JSONException e) {
			response=e.getMessage();
			return;
		}
	}
	
	@Override
	public String serve(String append) {
		this.processRequest();
		return append+response;
	}
}
