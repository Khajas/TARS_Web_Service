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
 * The following class is store an intent(a command, the category it belongs to
 * and the response that should be appended to the result)
 * @author Anwar
 */
public class Intents {
    private final String command;
    private final String category;
    private final String response;
    private static int id;
    /**
     * Constructor for Intents class
     * @param command
     *      A possible command to invoke the skill
     * @param category
     *      Category that given command belongs to
     * @param response
     *      Response that could be appended to the result received from API
     */
    public Intents(String command, String category, String response) {
            super();
            this.command = command;
            this.category = category;
            this.response=response;
    }
    /**
     * Copy constructor
     * @param intent
     *      An object of intent class
     */
    public Intents(Intents intent) {
            this(intent.getCommand(), intent.getCategory(), intent.getResponse());
    }
    /**
     * Returns the command of an intent
     * @return command 
     *      The command belonging to this intent
     */
    public String getCommand() {
            return command;
    }
    /**
     * Returns the category of an intent
     * @return category
     *      The category of this intent
     */
    public String getCategory() {
            return category;
    }
    /**
     * Returns the response of an intent
     * @return response
     *      Response that could be appended if this intent is called
     */
    public String getResponse(){
            return this.response;
    }
    /**
     * Returns the id of an intent
     * @return id
     *      Static ID belonging to an intent
     */
    public static int getId() {
            return id;
    }
    /**
     * Sets the id of an intent
     * @param id
     *      ID for an intent
     */
    @Deprecated
    public static void setId(int id) {
            Intents.id = id;
    }
}
/////////////////////// END OF SOURCE FILE  /////////////////