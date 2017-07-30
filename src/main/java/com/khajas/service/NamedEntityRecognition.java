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

package com.khajas.service;

import com.khajas.service.spellhandler.WordValidator;

/**
 * The following class is to separate named(recognizable words/words that belongs to intents)
 * and the words that doesn't belong to intents(named entity)
 */

public class NamedEntityRecognition {

	private final String query;
	private String namedEntity="";
	private String englishEntity="";
	private WordValidator wordValidator;
	/**
	 * Constructor that accepts user query
     * @param query
     * 		User query.
	 */
	public NamedEntityRecognition(String query){
		this.query=query;
		wordValidator= new WordValidator();
//		this.displayset();
		this.processQuery();
		System.out.println("Named entity: "+this.namedEntity );
		System.out.println("English entity: "+this.englishEntity );
	}

	/**
	 * Returns named  entity
	 * @return namedEntity
	 * 		Returns named entities(words that are not recognized by spell handler)
	 */
	public String getNamedEntity(){
		return this.namedEntity;
	}

	/**
	 * Returns english entity
	 * @return englishEntity
	 * 		Returns words that are recognized(one that are without spell errors) by spell hanlder.
	 */
	public String getEnglishEntity(){
		return this.englishEntity;
	}
	/**
	 * Processes the query and separates it into query words and named words
	 */ 
	private void processQuery(){
		String[] allwords = query.split(" ");
		for(int i=0;i<allwords.length;++i){
			String word=allwords[i];
			wordValidator.setWord(word.toLowerCase());
			if(wordValidator.isValidEnglishWord())
				englishEntity+=(word+" ");
			else namedEntity+=(word+" ");
		}
	}

	/**
	 * Display the set of all words from all intents from all skills
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private void displayset(){
		System.out.println("Set size: "+ApiCall.intent_words.size());
		for(String s: ApiCall.intent_words)
			System.out.println("Word: "+s);
	}
}
//////////////////////	END OF SOURCE FILE	////////////////////////////
