package com;

import java.lang.reflect.Method; 

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;



import model.AuthUser;


@Provider
public class SecurityFilter implements ContainerRequestFilter {
	public static final String AUTHENTICATION_HEADER_KEY = "Authorization";
	public static final String AUTHENTICATION_HEADER_PREFIX = "Basic ";

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		List<String> authHeader = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);
		Method method = resourceInfo.getResourceMethod();
		
		 if( ! method.isAnnotationPresent(PermitAll.class))
	        {
			//Access denied for all
	            if(method.isAnnotationPresent(DenyAll.class))
	            {
	            	Response unauthoriazedStatus = Response
							.status(Response.Status.UNAUTHORIZED)
							.entity("{\"error\" : \"not allowed 2\"}")
							.build();
	            		requestContext.abortWith(unauthoriazedStatus);
	            }
		if(authHeader != null && authHeader.size() > 0 ) {
			String authToken = authHeader.get(0);
			authToken = authToken.replaceFirst(AUTHENTICATION_HEADER_PREFIX, "");
			
			String decodedString = "";
			try {
				byte[] decodedBytes = Base64.getDecoder().decode(
						authToken);
			  decodedString = new String(decodedBytes, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			final StringTokenizer tokenizer = new StringTokenizer(
					decodedString, ":");
			
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();
			 if(method.isAnnotationPresent(RolesAllowed.class))
	            {
	                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
	                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
	                  
	                //Is user valid?
	                if( ! AuthUser.isUserAllowed(username, password, rolesSet))
	                {
	                	Response unauthoriazedStatus = Response
								.status(Response.Status.UNAUTHORIZED)
								.entity("{\"error\" : \"not authorized 3\"}")
								.build();
	                	requestContext.abortWith(unauthoriazedStatus);
	                   
	                }
	                return;
	            }
			
				
			
		}
	        }
		Response unauthoriazedStatus = Response
											.status(Response.Status.UNAUTHORIZED)
											.entity("{\"error\" : \"not authorized 1\"}")
											.build(); 
		requestContext.abortWith(unauthoriazedStatus);
	        
	}

}
