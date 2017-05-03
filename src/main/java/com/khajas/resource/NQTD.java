package com.khajas.resource;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.khajas.service.ApiCall;
import com.khajas.service.Intents;
import com.khajas.service.NamedEntityRecognition;
import com.khajas.service.GlobalDateTime.DateTimeApi;
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
	private String userIP;
	private LocationService ls;
	private WeatherInfo wi;
	private WikiApi wa;
	private NewsApi na;
	private DateTimeApi dta;
	private Maths ma;
	private CurrencyApi ca;
	private MyIdentification mi;
	private String userCity;
	private boolean debug;
	
	public NQTD(String userIP, String query){
		this.query=query;
		this.userIP=userIP;
		ls=new LocationService(this.userIP);
		this.userCity=ls.getUserCity();
		wi=new WeatherInfo(this.userCity);
		wa=new WikiApi();
		na=new NewsApi();
		dta=new DateTimeApi(this.userCity);
		ma=new Maths();
		ca=new CurrencyApi("USD");
		mi=new MyIdentification();
		this.printCommands();
	}
	
	public void printCommands(){
		ApiCall.print_commands();
	}
	private void captureLogs(String query, String response){
		try {
			String fileName="C:\\Users\\Anwar\\Desktop\\logs.txt";
			FileWriter fw=new FileWriter(fileName,true);
			fw.write(query+":	"+response+System.getProperty("line.separator"));
			fw.close();
			System.out.println("===========> Captured logs!");
		} catch (FileNotFoundException e) {
			System.out.println("Can't write to file!");
		} catch (IOException e) {
			System.out.println("Can't write to file!");
		}
	}
	public String detectServiceType(){
		query=query.replaceAll("please", "");
		NamedEntityRecognition ner=new NamedEntityRecognition(query);
		
		String query_words=ner.getEnglishEntity();
		if(query_words.isEmpty()){
			String search=ner.getNamedEntity();
			search=search.replaceAll(" ", "+");
			search=search.substring(0, search.length()-1);
			String regex="[\\d]+[+*%/-]+[\\d]+";
			Pattern pattern=Pattern.compile(regex);
			Matcher m = pattern.matcher(search);
			if(m.find()){
				ma.setQuery(search);
				response=ma.serve(" ");
//				return response;
			}
			else{
				String response="";
				search=search.replaceAll(" ","");
				System.out.println("Wiki querying: "+search);
				wa.setQuery(search);
				response=processString(wa.serve(""));
				if(!wa.status){
					wa.setQuery(search.toUpperCase());	// Else it may be an abreviation(AIMIM)
					response=processString(wa.serve(""));
				}
				this.response=response;
//				return response;
			}
		}
		Intents i=ApiCall.searchIntent(query_words);
		if(i==null) return "I don't understand!";
		if(i.getCategory().equals("locationservice")){
			System.out.println("Calling location service");
			response=processString(ls.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("weatherinfo")
				|| i.getCategory().equals("weatherinforemote")){
			if(!ner.getNamedEntity().isEmpty())
				wi.setCity(ner.getNamedEntity());
			System.out.println("Calling weather information");
			response=processString(wi.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("wikiapi")){
			String search=ner.getNamedEntity();
			search=search.replaceAll(" ","");
			System.out.println("Wiki querying: "+search);
			wa.setQuery(search);
			System.out.println("Calling wiki Api");
			response=processString(wa.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().contains("news")){
			na.setNewsLocation(i.getCategory());
			System.out.println("Calling News Api for : "+i.getCategory());
			response=processString(na.serve(i.getResponse()));
//			return response;
		}
		else if(i.getCategory().equals("datetime")){
			if(!ner.getNamedEntity().isEmpty())
				dta.setCity(ner.getNamedEntity());
			System.out.println("Calling date time api");
			response=dta.serve("It's");
//			return response;
		}
		else if(i.getCategory().equals("maths")){
			String exp=ner.getNamedEntity();
			exp=exp.replaceAll(" ", "+");
			if(!ner.getNamedEntity().isEmpty())
				ma.setQuery(exp.substring(0, exp.length()-1));
			System.out.println("Calling maths api");
			response=ma.serve("");
//			return response;
		}
		else if(i.getCategory().equals("currenyapi")){
			System.out.println("Calling Currency api");
			response=ca.serve(i.getResponse());
//			return response;
		}
		else if(i.getCategory().equals("me")){
			System.out.println("Calling self information");
			response=mi.serve(i.getResponse());
//			return response;
		}
		else response="I don't understand!";
		this.captureLogs(query, response);
		return response;
	}
	private String processString(String str){
		String response="";
		char[] chars=str.toCharArray();
		int count_open_parths=0;
		for(int i=0;i<chars.length;++i){
			if(chars[i]=='('){
				++count_open_parths;
			}
			if(chars[i]==')'){
				--count_open_parths;
				continue;
			}
			if(count_open_parths==0){
				response+=chars[i];
			}
		}
		response=response.replaceAll("[ ]+", " ");
		return response;
	}
}
