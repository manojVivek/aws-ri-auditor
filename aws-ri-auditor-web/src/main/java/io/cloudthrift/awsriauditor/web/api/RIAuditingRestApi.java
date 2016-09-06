/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package io.cloudthrift.awsriauditor.web.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author manojvivek
 *
 */
@Path("")
public class RIAuditingRestApi {
	
	@POST
	@Path("processAccount")
	public String processAccount(){
		return "Hello";
	}

}
