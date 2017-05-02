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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

@Path("/")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN+";charset=UTF-8")
public class RequestProcessor{
	public String userIP;
	@GET
	@Path("/query/{queryString}")
	public String wikiResponse(@Context HttpServletRequest requestContext,@PathParam("queryString") String query) throws ClientProtocolException, IOException{
		userIP=requestContext.getRemoteAddr();
		query=query.replaceAll("\\+"," ");
		userIP=requestContext.getRemoteAddr();	// Let's use this IP for geo location mapping
		if(userIP.contains("127.0")) userIP="2601:242:4000:be10:1acf:5eff:fedc:9a68";
		NQTD nqt=new NQTD(userIP, query);
		return nqt.detectServiceType();
	}
}
