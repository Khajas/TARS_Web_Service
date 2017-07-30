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
		super.addIntent("Hello TARS", "me", "Hi");
		super.addIntent("Hi tars", "me", "Hello!");
		super.addIntent("Hey tars", "me", "Hello!");
		
		super.addIntent("Hello", "me", "Hi");
		super.addIntent("Hi", "me", "Hello!");
		super.addIntent("Hey", "me", "Hello!");
		
		super.addIntent("How are you doing", "me", "I'm doing great!\nHow about you?");
		super.addIntent("How are you", "me", "I'm doing great!\nHow about you?");
		
		super.addIntent("Good Morning", "me", "Have a good one ");
		super.addIntent("Good Afternoon", "me", "Have a good one ");
		super.addIntent("Good Evening", "me", "Have a good one ");
		
		super.addIntent("wassup", "me", "Nothing much! ");
		super.addIntent("What's the update", "me", "Hmmm ");
		
		super.addIntent("Okay","me", "No problem!");
		super.addIntent("yes", "me", "Alright!");
		super.addIntent("no", "me", "I see");

		super.addIntent("I'm doing great", "me", "Wonderful!");
		super.addIntent("I'm good", "me", "Wonderful!");
		super.addIntent("I'm fine", "me", "Wonderful!");
		super.addIntent("fine", "me", "Okay!");
		
		super.addIntent("sorry", "me", "It's alright!");
		super.addIntent("thank", "me", "Anytime!");
		super.addIntent("alright", "me", "No problem!");
		super.addIntent("thanks for help", "me", "That's why I'm here!");
	}

	/**
    * Makes a call to request processor( method processRequest())
    * and returns the response appended by the 'append' parameter.
    * @param append
    * 		A customized string that should be appended to response.
    * @return response
    * 		Response after processing request from user.
    */	
	@Override
	public String serve(String append) {
		return append;
	}
	/**
	 * Process user request.
	 */
	@Deprecated
    @Override
    public String processRequest(String query){
        return "NO SUPPORTED METHOD IMPLEMENTAION";
    }
}
//////////////////	END OF SOURCE FILE	////////////////////////