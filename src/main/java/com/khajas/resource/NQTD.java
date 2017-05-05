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
package com.khajas.resource;
/**
 * The following class NQTD(New Query Type Detector) is to detect the type of an intent.
 * It has objects of all possible skills, if a skill is detected above the fidelity score
 * serve() method( an abstract method for all the api calls) is called and response is
 * sent to the requester or client or app
 * @author Anwar initial api and initial documentation.
 */
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.khajas.service.ApiCall;
import com.khajas.service.Intents;
import com.khajas.service.NamedEntityRecognition;
import com.khajas.service.GlobalDateTime.DateTimeApi;
import com.khajas.service.cricscore.CricApi;
import com.khajas.service.currency.CurrencyApi;
import com.khajas.service.locationservice.LocationService;
import com.khajas.service.maths.Maths;
import com.khajas.service.news.NewsApi;
import com.khajas.service.personal.MyIdentification;
import com.khajas.service.weatherinfo.WeatherInfo;
import com.khajas.service.wiki.WikiApi;

public class NQTD {
	private String query;
	private String response;
	private final String userIP;
	private final String userCity;
	// Objects for all skill supported by TARS
	private final LocationService ls;
	private final WeatherInfo wi;
	private final WikiApi wa;
	private final NewsApi na;
	private final DateTimeApi dta;
	private final Maths ma;
	private final CurrencyApi ca;
	private final MyIdentification mi;
	private final CricApi ckt_a;
	
	// Add any new skill object above this line
	@SuppressWarnings("unused")
	private boolean debug;
	/**
	 *Constructor for NQTD class, it accepts an userIP, and query
	 *@param userIP
	 *@param query
	 */
	
	public NQTD(String userIP, String query){
		this.query=query;
		this.userIP=userIP;
	// Initializing all the objects for skills
		ls=new LocationService(this.userIP);
		this.userCity=ls.getUserCity();		// Once the location is determined based on IP, get the user city
		wi=new WeatherInfo(this.userCity);	// Pass this city as default for weather information
		wa=new WikiApi();
		na=new NewsApi();
		dta=new DateTimeApi(this.userCity);	// Pass this city as default for DateTime information
		ma=new Maths();
		ca=new CurrencyApi("USD");		// The default base for currency
		mi=new MyIdentification();
		ckt_a=new CricApi();


	// Initialize a new object above this line
		this.printCommands();
	}
	/**
	 *Prints commands from all intents 
	 */
	private void printCommands(){
		ApiCall.print_commands();	// Static method from ApiCall class
	}
	/**
	 * Stores user query and TARS response on local file
	 * for improving services.
	 * @param query
	 * @param response
	 */
	private void captureLogs(String query, String response){
		try {
			String fileName=".//logs.txt";
                    try (FileWriter fw = new FileWriter(fileName,true)) {
                        fw.write(query+":	"+response+System.getProperty("line.separator"));
                        //		System.out.println("Captured logs!");
                    }
		} catch (FileNotFoundException e) {
			System.out.println("No such file! or "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Can't write to file! or "+e.getMessage());
		}
	}
	/**
	 * Detects the type of service and calls serve() method on skill object
	 * and returns the response to caller.
	 * @return response
	 */
	public String detectServiceType(){
		query=query.replaceAll("please", "");				// Replace any requests if present
		NamedEntityRecognition ner=new NamedEntityRecognition(query);
		String query_words=ner.getEnglishEntity();			// Get the query words(words that are present in any of the intent commands)
		if(query_words.isEmpty()){
			String search=ner.getNamedEntity();			// If there are no query words, get the unrecognized or named words
			search=search.replaceAll(" ", "+");			// Replace all the '+' with ' ' to recover the string from URL format
			search=search.substring(0, search.length()-1);		// To avoid the last ' ' contained in the string
			String regex="[\\d]+[+*%/-]+[\\d]+";			// Regex to detect mathematical expressions	
			Pattern pattern=Pattern.compile(regex);
			Matcher m = pattern.matcher(search);
			if(m.find()){
				ma.setQuery(search);				// If it's a mathematical expression call serve() on maths api
				response=ma.serve(" ");
//				return response;
			}
			else{
				String wikiResponse;
				search=search.replaceAll(" ","");		// Remove all spaces to make the URL format for wiki api
				System.out.println("Wiki querying: "+search);
				wa.setQuery(search);
				wikiResponse=processString(wa.serve(""));
				if(!wa.status){					// If there is no proper response from wiki, make all upper case letters
					wa.setQuery(search.toUpperCase());	// eg: It may be an abreviation(AIMIM)
					wikiResponse=processString(wa.serve(""));
				}
				this.response=wikiResponse;
//				return response;
			}
		}
		// Resolve the query to get the type of skill of intent
		Intents i=ApiCall.searchIntent(query_words);
		if(i==null) return "I don't understand!";			// If the intent is not recognizable
		if(i.getCategory().equals("locationservice")){
			System.out.println("Calling location service");		// For resolving location requests(Eg: Where am I now?)
			response=processString(ls.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("weatherinfo")
				|| i.getCategory().equals("weatherinforemote")){	// For resolving weather services(Eg: weather of Chicago)
			if(!ner.getNamedEntity().isEmpty())
				wi.setCity(ner.getNamedEntity());
			System.out.println("Calling weather information");
			response=processString(wi.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("wikiapi")){			// For resolving wiki service(eg: What's a triangle)
			String search=ner.getNamedEntity();
			search=search.replaceAll(" ","");
			System.out.println("Wiki querying: "+search);
			wa.setQuery(search);
			System.out.println("Calling wiki Api");
			response=processString(wa.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().contains("news")){			// For resolving news requests(Eg: Please show Indian headlines)
			na.setNewsLocation(i.getCategory());
			System.out.println("Calling News Api for : "+i.getCategory());
			response=processString(na.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("datetime")){			// For resolving date time requests(Eg: Current time at Tokyo)
			if(!ner.getNamedEntity().isEmpty())
				dta.setCity(ner.getNamedEntity());
			System.out.println("Calling date time api");
			response=dta.serve("It's");
//			return response;
		}
		else if(i.getCategory().equals("maths")){			// For resolving maths expressions(Eg: what is 1+3 ?)
			String exp=ner.getNamedEntity();
			exp=exp.replaceAll(" ", "+");
			if(!ner.getNamedEntity().isEmpty())
				ma.setQuery(exp.substring(0, exp.length()-1));
			System.out.println("Calling maths api");
			response=ma.serve("");
//			return response;
		}
		else if(i.getCategory().equals("currenyapi")){			// For resolving exchange rates(Eg: What's is today's Exchange rate ?)
			System.out.println("Calling Currency api");
			response=ca.serve(i.getResponse());
//			return response;
		}
		else if(i.getCategory().equals("me")){				// For resolving contextual skills(Eg: Hello )
			System.out.println("Calling self information");
			response=mi.serve(i.getResponse());
//			return response;
		}
		else if(i.getCategory().equals("iplscore")){			// For resolving ipl score or cricket score updates
			System.out.println("Calling IPL Api");
			response=ckt_a.serve("IPL Score: ");
		}
		else response="I don't understand!";				// In case there is no match
		this.captureLogs(query, response);				// Once the query is resolved, store the logs
		return response;
	}
	/**
	 * Process the response returned from wikipedia, since the other languages may not be understood
	 * or read out loud.
	 * @return processed_response
	 */
	private String processString(String str){
		String processed_response="";
		char[] chars=str.toCharArray();
		int count_open_parths=0;
		for(int i=0;i<chars.length;++i){
			if(chars[i]=='('){
				++count_open_parths;
			}
			if(chars[i]==')'){
				--count_open_parths;	// Ignore anything that is between '(' and ')'
				continue;
			}
			if(count_open_parths==0){
				processed_response+=chars[i];
			}
		}
		processed_response=processed_response.replaceAll("[ ]+", " ");
		return processed_response;
	}
}
////////////////////////////	END OF SOURCE FILE	//////////////////////////////////////