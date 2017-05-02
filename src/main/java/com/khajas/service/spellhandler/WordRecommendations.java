/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khajas.service.spellhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Anwar
 */
public class WordRecommendations {

    /**
     * @param args the command line arguments
     */
	private String word;
	private Trie trie;
	public WordRecommendations(String word){
		trie=new Trie();
		this.word=word;
		this.builTrie();
	}
    public void builTrie(){
        try{
            readData(trie, "C:\\Users\\Anwar\\workspace\\TARS\\words.txt");
        }catch(FileNotFoundException e){
            System.out.println("Missing words File!!");
            return;
        }
//        print(trie);
        String input_word=getUserInput();
        while(input_word.equalsIgnoreCase("$")){
            System.out.print("Type a complete or partial word: ");
            input_word=getUserInput();
        }
//        trie.getSuggestion(input_word);
    }
    public String getUserInput(){
    	return this.word;
    }
    @SuppressWarnings("resource")
	private void readData(Trie trie, String fileName) throws FileNotFoundException{
        Scanner sc;
        sc=new Scanner(new File(fileName));
        String pattern="[A-Za-z\n\']+";
        while(sc.hasNext(pattern)){
            if(sc.hasNext("\n")) continue;
            String word=sc.next(pattern);
//          System.out.println(word); // shows word read
            trie.addWord(word);
        }
    }
    public boolean isValidEnglishWord(){
    	return trie.isValidEnglishWord(word);
    }
}
