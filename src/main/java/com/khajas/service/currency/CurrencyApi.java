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
package com.khajas.service.currency;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.khajas.service.ApiCall;
/**
 * Supports currency skills, it calls currency api registered at
 * http://fixer.io/
 * @author - Anwar
 */
public class CurrencyApi extends ApiCall{
	private String base="USD";	// Base conversion factor
	private String response;

    /**
     * Constructor for currency skill
     */
	private CurrencyApi(){
		// Add the expected intents that the user may ask TARS
		super.addIntent("What's a dollar worth ", "currenyapi", "$1: ");
		super.addIntent("dollar rate ", "currenyapi", "1 USD: ");
		super.addIntent("1$ ", "currenyapi", "1 USD: ");
		super.addIntent("Exchange rates ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("Today's exchange rate ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("What is today's exchange rate ", "currenyapi", "Exchange Rate(1 USD): ");
		super.addIntent("Today's currency rate ", "currenyapi", "Currency Rate(1 USD): ");
		super.addIntent("What is today's currency rate ", "currenyapi", "Currency Rate(1 USD): ");
	}

	/**
     * Constructor for base other than USD
     * A base must be valid, for more information visit http://fixer.io
     * @param base
     */
	public CurrencyApi(String base){
		this();
		this.base=base;
	}

    /**
     * Process the request by calling the URL for currency api 
     * and prepare the response, it also makes a call to super class- 
     * method to get the JSON response.
     * @return 
     */	
        @Override
	public String processRequest(String query){
		response="Sorry, I can't fetch currency details!";
		URL url;
		try {
			url = new URL("http://api.fixer.io/latest?base="+base);
		} catch (MalformedURLException e) {
			response=e.getMessage();
			return response;
		}
		JSONObject json;
		try {
			json = super.getJsonObject(url);
			double EUR=json.getJSONObject("rates").getDouble("EUR");
			double GBP=json.getJSONObject("rates").getDouble("GBP");
			double CAD=json.getJSONObject("rates").getDouble("CAD");
			double INR=json.getJSONObject("rates").getDouble("INR");
			double AUD=json.getJSONObject("rates").getDouble("AUD");
			// You may add more currencies as supplied by fixer.io
			response="\nEUR: "+EUR
					+"\nGBP: "+GBP
					+"\nCAD: "+CAD
					+"\nINR: "+INR
					+"\nAUD: "+AUD;
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
		this.processRequest("");    // There is no need for user query paramter
		return append+response;
	}
}
///////////////////////		END OF SOURCE FILE	//////////////////////////