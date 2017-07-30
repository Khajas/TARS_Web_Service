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
package com.khajas.service.wiki;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 
 * @author Anwar
 *
 */
@Deprecated
public class WikiService {
	private boolean hints=false;
	private boolean showFullPage;
	/**
	 * Constructor for wikiResponse API
	 * @param query
	 * 		User query.
	 * @return processedResponse
	 * 		Returns processed string after querying the wikipedia
	 * @throws ClientProtocolException
	 * 		Signals an error in the HTTP protocol.
	 * @throws IOException
	 * 		Signals that an I/O exception of some sort has occurred.
	 */
	public String wikiResponse(String query) throws ClientProtocolException, IOException{
		final CloseableHttpClient client = HttpClients.createDefault();
		String processedQuery=processQuery(query);
		if(hints)	System.out.println(processedQuery);
		String linkToWiki=getWikiLink(processedQuery);
        HttpGet get = new HttpGet(linkToWiki);
        final HttpResponse response = client.execute(get);
//        final int responseCode = response.getStatusLine().getStatusCode();
        final HttpEntity entity = response.getEntity();
        final InputStream responseBody = entity.getContent();
//        assertEquals(200, responseCode); This is always true, since google never fails to respond
        if(showFullPage)
        	System.out.println(IOUtils.toString(responseBody));
        Document doc = Jsoup.parse(IOUtils.toString(responseBody));
        Elements head = doc.select("p");
        String processedResponse=null;
        int count=0;
        for(Element e: head){
        	processedResponse=processResponse(e.text()+"\n");
	        if(processedResponse.startsWith("Coordinates"))
	        	continue;
	        System.out.println(processedResponse); ++count;
	        if(processedResponse.contains("may refer to") || processedResponse.contains("most often refers"))
	            	return processedResponse+=getLiTags(doc);
	        if(count==1) break;
        }
        client.close();
		return processedResponse;
	}
	/**
	 * This method is invoked if a tag refers to a list of possibilities
	 * @param document
	 * 		Document datatype that should be parsed for extracting the data.
	 * @return processedResponse
	 * 		Returns response after processing the document.
	 */
	private String getLiTags(Document document){
		String processedResponse="";
		Elements head=document.select("li");
		int count=0;
		for(Element e: head){
			processedResponse+= processResponse(e.text()+"\n");
	        System.out.println(processedResponse);
	        count++;
	        if(count==3) break;	// Display top three items from the list
		}
		return processedResponse;
	}
	
	/**
	 * This method takes a raw response and returns a processed reponse
	 * @param partialProcessedResponse
	 * 		Takes partial processed string that might contain '\n' or special characters.
	 * @return processedString
	 * 		Returns processed string after removing special characters.
	 */
	private String processResponse(String partialProcessedResponse){
        String processedString = partialProcessedResponse.replaceAll("\\(.*?\\)","");    	// Remove anything between ()
        processedString = processedString.replaceAll("\\[.*?\\]", "");          	// Remove anything between []
        processedString = processedString.replaceAll("\\. ", ".\n");          		// Give newline to each string
        return processedString;
	}
	/**
	 * This method takes a raw query and returns a searchable query by replaces spaces with + signs
	 * @param rawQuery
	 * 		Raw query that is googled to get the acutal link for wikipedia
	 * @return GoogleSearchabelQuery
	 * 		Returns google searchable query.
	 */
	private String processQuery(String rawQuery){
		String searchableQuery="wiki+";
		searchableQuery+=rawQuery.replaceAll("[ ]+", "+");
		if(hints) System.out.println("Searchable query: "+searchableQuery);
		return searchableQuery;
	}
	/**
	 * This method takes a query parameter and returns the link to it's wiki page
	 * @param searchableQuery
	 * 		A query string(with FQDN) that could be called by HTTP connection.
	 * @return linkToWiki
	 * 		HTTP link to wikipedia.
	 * @throws IOException
	 * 		Signals that an I/O exception of some sort has occurred.
	 * @throws ClientProtocolException
	 * 		Signals an error in the HTTP protocol. 
	 */
	private String getWikiLink(String searchableQuery) throws ClientProtocolException, IOException{
		String linkToWiki=null;
		final CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://www.google.com/search?q="+searchableQuery);
		if(hints) System.out.println("Querying: https://www.google.com/search?q="+searchableQuery);
        final HttpResponse response = client.execute(get);
        final int responseCode = response.getStatusLine().getStatusCode();
        final HttpEntity entity = response.getEntity();
        final InputStream responseBody = entity.getContent();
        assertEquals(200, responseCode);
        linkToWiki=extractWiki(IOUtils.toString(responseBody));
        return linkToWiki;
	}
	/**
	 * This method is invoked over the response from google
	 * @param responseBody
	 * 		Takes response body for after searching google.
	 * @return extractedLinkOfWikipedia
	 * 		Returns link extracted from the search page
	 */
	private String extractWiki(String responseBody){
		String pattern="https\\:\\/\\/en\\.wikipedia\\.org\\/wiki\\/([a-zA-Z_-]*)";
		Pattern r=Pattern.compile(pattern);
		Matcher m= r.matcher(responseBody);
		if(m.find()){
			if(hints)
				System.out.println("String prepared: "+m.group(1));
		}
		return m.group(0).toString();
	}
}
/////////////////////	END OF SOURCE FILE		////////////////////////////