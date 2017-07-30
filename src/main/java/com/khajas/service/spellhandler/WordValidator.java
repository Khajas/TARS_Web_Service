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
package com.khajas.service.spellhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *	Validates a word it checks if it belong to language english(by searching into the trie formed by words.txt file).
 * @author Anwar
 */
public class WordValidator {

	private String word;
	private Trie trie;
	/**
	 * Default constructor
	 * Initializes Trie object and calls buildTrie() method.
	 */
	public WordValidator(){
		trie=new Trie();
		this.buildTrie();
	}
	/**
	 * Constructor for word validator
	 * @param word
	 * 		Word that needs to be validated.
	 */
	public WordValidator(String word){
		this();
		this.word=word;
	}
	/**
	 * Sets the word that needs to be validated
	 * @param word
	 * 		Word that needs to be validated.
	 */
	public void setWord(String word){
		this.word=word;
	}
	/**
	 * Builds Trie by reading data from the file
	 */
    public void buildTrie(){
        try{
            readData(trie, "C:\\Users\\Anwar\\workspace\\TARS_Web_Service\\src\\"
            		+ "main\\java\\com\\khajas\\service\\spellhandler\\words.txt");
        }catch(FileNotFoundException e){
            System.out.println("Missing words File!!");
            return;
        }
    }
    /**
     * Returns the word that needs to be validated
     * @return word
     * 		Word that needs to be validated.
     */
    public String getWord(){
    	return this.word;
    }
    /**
     * Reads words from file and make a trie data structure
     * @param trie
     * 		Trie created from the given set of words.
     * @param fileName
     * 		Location of file containing words.
     * @throws FileNotFoundException
     * 		 Signals that an attempt to open the file denoted by a specified pathname has failed.
     */
    @SuppressWarnings("resource")
	private void readData(Trie trie, String fileName) throws FileNotFoundException{
        Scanner sc;
        sc=new Scanner(new File(fileName));
        String pattern="[A-Za-z\n\']+";
        while(sc.hasNext(pattern)){
            if(sc.hasNext("\n")) continue;
            String word=sc.next(pattern);
            trie.addWord(word);
        }
    }
    /**
     * Tests if a word is a valid english word
     * @return boolean
     * 		True if a word is valid else false.
     */
    public boolean isValidEnglishWord(){
    	return trie.isValidEnglishWord(word);
    }
}
//////////////////////		END OF SOURCE FILE	/////////////////////////