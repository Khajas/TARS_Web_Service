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

package com.khajas.service.personal;

import com.khajas.service.ApiCall;
/**
 * Supports personal identification
 * @author - Anwar
 */
public class MyIdentification extends ApiCall{
	
	@SuppressWarnings("unused")
	private String query;
	@SuppressWarnings("unused")
	private String response;
	
	/**
	 * Constructor for personal identification skills
     */
	public MyIdentification(){
		super.addIntent("Hello ", "me", "Hi");
		super.addIntent("hi ", "me", "Hello!");
		super.addIntent("Hey ", "me", "Hello!");

		super.addIntent("Hello TARS", "me", "Hi");
		super.addIntent("hi tars", "me", "Hello!");
		super.addIntent("Hey tars", "me", "Hello!");
		
		super.addIntent("How are you doing ", "me", "I'm doing great!\nHow about you?");
		
		super.addIntent("Good Morning ", "me", "Good ");
		super.addIntent("Good Afternoon ", "me", "Good ");
		super.addIntent("Good Evening ", "me", "Good ");
		
		super.addIntent("Wassup? ", "me", "Nothing much! ");
		super.addIntent("wassup ", "me", "Nothing much! ");
		super.addIntent("What's the update ", "me", "Hmmm ");
		
		super.addIntent("Okay ","me", "No problem!");
		super.addIntent("yes ", "me", "Alright!");
		super.addIntent("no ", "me", "I see");

		super.addIntent("I'm doing great ", "me", "Wonderful!");
		super.addIntent("I'm good ", "me", "Wonderful!");
		super.addIntent("I'm fine ", "me", "Wonderful!");
		
		super.addIntent("sorry ", "me", "It's alright!");
		super.addIntent("thank you ", "me", "anytime");
		super.addIntent("thanks ", "me", "anytime");
		super.addIntent("alright! ", "me", "no problem!");
		super.addIntent("thanks for help! ", "me", "That's why I'm here!");
	}

	/**
     * Makes a call to request processor( method processRequest())
     * and returns the response appended by the 'append' parameter.
     * @param append
     * @return response
     */	
	@Override
	public String serve(String append) {
		return append;
	}

}
//////////////////	END OF SOURCE FILE	////////////////////////
