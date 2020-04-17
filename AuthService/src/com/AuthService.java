package com;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.UserBean;
import model.AuthModel;


@Path("/users")
public class AuthService {

 AuthModel authObj = new AuthModel();
	
	@RolesAllowed({"admin"})
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readusers() {
		List <UserBean> list;
		Response response;
		
		
		list =authObj.readUsers();
		 response=Response.ok(authObj.readUsers()).build();
		
		
		if (!list.isEmpty()) {
		return response;
		}
		return Response.noContent().build(); 
	}
	
	@RolesAllowed("admin")
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readUserById(@PathParam("userId") int id) {
		UserBean user = authObj.readUserById(id);
		if (user !=null) {
			return	Response.ok().entity(authObj.readUserById(id)).build();
		}
		return	Response.noContent().build();
		}
	
	
	/*@RolesAllowed({"admin"})
	@POST
	@Path("/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean check(String data) {
		
		 JSONObject jsonObject = new JSONObject(data);
		 String username = jsonObject.getString("username");
		 String password = jsonObject.getString("password");
		 Set<String> roleSet = (Set<String>) jsonObject.getJSONObject("roleSet");
		
		return	AuthUser.isUserAllowed(username, password, roleSet);
		
		
	} 
	*/
	
	@RolesAllowed({"admin"})
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserUsers(UserBean user) {
		Response response =	authObj.insertUsers(user);
		return response;
		
		
	} 
	
	@RolesAllowed({"admin"})
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("userId") int userId,UserBean user) {
		user.setId(userId);
		return	authObj.updateUser(user);
						
	}
	
	@RolesAllowed({"admin"})
	@DELETE
	@Path("/{userId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteUser(@PathParam("userId") int userId) {
		return authObj.deleteUser(userId);		
			
	}
	
	@RolesAllowed({"doctor","admin"})
	@GET
	@Path("/doctor")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkDoc() {
	 
		return true;
				
	}
	
	@RolesAllowed({"admin"})
	@GET
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkAdmin() {
	 
		return true;
				
	}
	
	@RolesAllowed({"patient","admin"})
	@GET
	@Path("/patient")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkPatient() {
	 
		return true;
				
	}
	
	
	
	
}
