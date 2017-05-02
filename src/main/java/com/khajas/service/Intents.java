package com.khajas.service;

public class Intents {
	private final String command;
	private final String category;
	private final String response;
	private static int id;
	public Intents(String command, String category, String response) {
		super();
		this.command = command;
		this.category = category;
		this.response=response;
	}
	public Intents(Intents i) {
		this(i.getCommand(), i.getCategory(), i.getResponse());
	}
	public String getCommand() {
		return command;
	}
	public String getCategory() {
		return category;
	}
	public String getResponse(){
		return this.response;
	}
	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		Intents.id = id;
	}
}
