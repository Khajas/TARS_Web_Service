package com.khajas.service;

public class NamedEntityRecognition {
	private String query;
	private String namedEntity="";
	private String englishEntity="";
	public NamedEntityRecognition(String query){
		this.query=query;
		this.displayset();
		this.processedQuery();
		System.out.println("Named entity: "+this.namedEntity );
		System.out.println("English entity: "+this.englishEntity );
	}
	public String getNamedEntity(){
		return this.namedEntity;
	}
	public String getEnglishEntity(){
		return this.englishEntity;
	}
	public void processedQuery(){
		String[] allwords = query.split(" ");
		for(int i=0;i<allwords.length;++i){
			String word=allwords[i];
			if(ApiCall.intent_words.contains(word.toLowerCase()))
				englishEntity+=(word+" ");
			else namedEntity+=(word+" ");
		}
		
	}
	public void displayset(){
		System.out.println("Set size: "+ApiCall.intent_words.size());
		for(String s: ApiCall.intent_words)
			System.out.println("Word: "+s);
	}
}
