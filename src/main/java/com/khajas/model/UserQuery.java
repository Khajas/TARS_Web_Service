package com.khajas.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserQuery {
	String query;
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}	
}
