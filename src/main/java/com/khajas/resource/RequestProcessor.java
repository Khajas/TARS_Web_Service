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
package com.khajas.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import com.khajas.model.UserQuery;

/**
 * The following class is to receive the user query and return the response after processing 
 * @author - Anwar
 */

@Path("/")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN+";charset=UTF-8")
public class RequestProcessor{
	public String userIP;
	@POST
	@Path("/query")
	/**
	 * The first method that gets called when a user contacts TARS web service.
	 * @param requestContext
	 * 		Captures client details.
	 * @param query
	 * 		User query.
	 * @return response
	 * 		Response after processing user query
	 * @throws ClientProtocolException
	 * 		Signals an error in the HTTP protocol.
	 * @throws IOException
	 * 		Signals that an I/O exception of some sort has occurred.
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	public String TARS_Response(UserQuery query, @Context HttpServletRequest requestContext) 
						throws ClientProtocolException, IOException{
		userIP=requestContext.getRemoteAddr();		// Get user ip address
		userIP=requestContext.getRemoteAddr();		// Let's use this IP for geo location mapping
		if(userIP.contains("127.0")) 			// If it's local executed, let's make the IP as my default router's IP
			userIP="2601:242:4000:be10:1acf:5eff:fedc:9a68";
		System.out.println("Query before being passed to QueryTypeDetector: "+query.getQuery());
		QueryTypeDetector nqt=new QueryTypeDetector(userIP, query.getQuery());		// Detect the query type
		return nqt.detectServiceType();			// and return the response from the detected object
	}
}
/////////////////////////	END OF SOURCE FILE	////////////////////////////////////