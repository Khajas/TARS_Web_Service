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
/**
 * The following class is to capture properties of an intent( command, category, response)
 * @author - Anwar
 */
public class Intents {
	private final String command;
	private final String category;
	private final String response;
	// Common id that is shared among all the intents, it's not being using currently
	private static int id;
	/**
	 * Constructor for Intents class
	 * @param command
	 * @param category
	 * @param response
	 */
	public Intents(String command, String category, String response) {
		super();
		this.command = command;
		this.category = category;
		this.response=response;
	}
	
	/**
	 * Copy Constructor
         * @param i
	 */
	public Intents(Intents i) {
		this(i.getCommand(), i.getCategory(), i.getResponse());
	}

    /**
     * Returns the command
     * @return command
     */
	public String getCommand() {
		return command;
	}

    /**
     * Returns the category
     * @return category
     */
	public String getCategory() {
		return category;
	}
	
    /**
     * Returns the response(that should be appended)
     * @return response
     */
	public String getResponse(){
		return this.response;
	}

    /**
     * Returns id of an intent
     * @return id
     */
	public static int getId() {
		return id;
	}
	
    /**
     * Sets id to an intent
     * @param id
     */
	public static void setId(int id) {
		Intents.id = id;
	}
}
////////////////////	END OF SOURCE FILE	//////////////////////////////////
