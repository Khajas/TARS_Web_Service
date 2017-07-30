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

import java.util.ArrayList;

/**
 *
 * @author Anwar
 */
public class Trie {
    private final Node root;
    public Trie(){
        root=new Node('$');
    }
    /**
    * Method to add a word to a Trie
    * @param word
    * 		Word that needs to be added
    * @return boolean
    * 		True if the word is added, else false
    */
    public boolean addWord(String word){
        Node n=root;
        if(validateWord(word)){
            for(int i=0;i<word.length();++i){
                    n=n.addChild(word.charAt(i));
            }
            if(n.hasChild('*')) return false;
            else n.addChild('*');
        }
        else return false;
        return true;
    }
    /**
     * Validates a word if it contains all English letters(excluding symbols), checks character by character.
     * @param word
     * 		Word that needs to be validated.
     * @return boolean
     * 		True if a word is valid else false.
     */
    private boolean validateWord(String word){
        for(int i=0;i<word.length();++i)
            if(!Character.isAlphabetic(word.charAt(i)))
                return false;
        return true;
    }
    /**
     * Check if a word is spelled correctly
     * @param word
     * 		Word that needs to be checked.
     * @return boolean
     * 		True if a word is valid else false.
     */
    public boolean isValidEnglishWord(String word){
    	if(!validateWord(word)) return false;
    	String result=this.getSuggestion(word);
    	return result.equals("CompleteWord");
    }
    
    /**
     * Prints each word from a given node
     * @param node
     * 		Node from which all the possible words will be printed.
     * @param word_so_far
     * 		Word's printed so far, any child nodes will be appended to get the new word formed by the child node.
     */
    @Deprecated
    private void printEachWord(Node node, String word_so_far){
        if(node==null){
            return;
        }
        if(node.getChar()=='*'){
            System.out.println(word_so_far);
            return;
        }
        ArrayList<Node> arr=node.getAllChildNodes();
        for(Node nn: arr){
            if(nn!=null && nn.getChar()!='*')
                printEachWord(nn,word_so_far+nn.getChar());
            else printEachWord(nn,word_so_far);
        }
    }
    /**
     * Print each word from the start(prints all words)
     * @param word_so_far
     * 		Word formed so far.
     */
    @Deprecated
    public void printEachWord(String word_so_far){
        this.printEachWord(root, word_so_far);
    }
    /**
     * Gives word suggestions for a partial word.
     * Checks if a word is spelled correctly.
     * Checks if a word is complete.
     * @param word
     * 		Word that needs checking.
     * @return test_result
     * 		Returns test results.
     */
    public String getSuggestion(String word){
    	System.out.println("Checking for word:"+word+".");
        if(word==null || word.isEmpty()) return new String("Empty String");
        Node n=root;
        for(int i=0;i<word.length();++i){
            char ch=word.charAt(i);
            System.out.println("Checking for character: "+ch+".");
            if(n.hasChild(ch)){
                n=n.getNode(ch);
            }
            else{
                System.out.println("Spelled Wrong");
                return new String("SpelledWrong");
            }
        }
        if(!n.hasChild('*')){
            System.out.println("Do you mean? ");
            for(Node nn: n.getAllChildNodes()){
                    printEachWord(nn, word+nn.getChar());
            }
            return new String("IncompleteWord");
        }else{
            System.out.println("Is A complete word!!");
            return new String("CompleteWord");
        }
    }
}
///////////////////////		END OF SOURCE FILE	//////////////////////////