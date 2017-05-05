package com.khajas.service.footballApi;

import com.khajas.service.ApiCall;

public class FootballApi extends ApiCall{
	private String response;
	
	public FootballApi(){
		super.addIntent("Football score ", "footballscores", "");
		super.addIntent("Football scores ", "footballscores", "");
		super.addIntent("Football score ", "footballscores", "");
	}
	
	@Override
	public String serve(String append) {
		return null;
	}

}
