package com;

import javax.ws.rs.Path;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Hospital;

@Path("/Hospitals")
@PermitAll
public class HospitalService {

	Hospital hospital = new Hospital();
	
	@RolesAllowed("admin")
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addHospitals(String hosData)
	{
		JsonObject hosObject = new JsonParser().parse(hosData).getAsJsonObject();

		//`hospitalName`, `hospitalphoneNo`, `hospitalAddress`, `hospitalEmail`
		
		String name = hosObject.get("hospitalName").getAsString();
		String no = hosObject.get("hospitalphoneNo").getAsString();
		String address = hosObject.get("hospitalAddress").getAsString();
		String email = hosObject.get("hospitalEmail").getAsString();

		String output = hospital.addHospitals(name, no, address, email);

		return output;
	}
	
	@RolesAllowed({ "admin","patient" })
	@GET
	@Path("/readHospital")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors()
	{
		return hospital.readHospital();
	}
	
	@RolesAllowed({ "admin","doctor" })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String hosData)
	{
		JsonObject hosObject = new JsonParser().parse(hosData).getAsJsonObject();
				//JsonParser().parse(docData).getAsJsonObject();
	//`hospitalID`, `hospitalName`, `hospitalphoneNo`, `hospitalAddress`, `hospitalEmail`
		String id = hosObject.get("hospitalID").getAsString();
		String name = hosObject.get("hospitalName").getAsString();
		String no = hosObject.get("hospitalphoneNo").getAsString();
		String address = hosObject.get("hospitalAddress").getAsString();
		String email = hosObject.get("hospitalEmail").getAsString();
		
		String output = hospital.updateDoctor(id, name, no, address, email);
		return output;
		
	}
	
	@RolesAllowed("admin")
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String hosData)
	{
		JsonObject jsonObject = new JsonParser().parse(hosData).getAsJsonObject();
		
		String id = jsonObject.get("hospitalID").getAsString();
		
		String output = hospital.deleteHospital(id);
		
		return output;
	}
	
	@RolesAllowed({"admin","patient"})
	@GET
	@Path("/searchDoc/{hospitalName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String searchDoc(@PathParam("hospitalName") String docData)
	{
		
		return hospital.searchHospital(docData);
	}
	
	
}
