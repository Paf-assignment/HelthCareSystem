package com;

import javax.annotation.security.PermitAll;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Doctor;

@Path("/Doctors")
@PermitAll
public class DoctorService {
	
	Doctor doc = new Doctor();
	
	@RolesAllowed("admin")
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addDoctor(String docData)
	{
		JsonObject docObject = new JsonParser().parse(docData).getAsJsonObject();
		
		String name = docObject.get("dName").getAsString();
		String specialization = docObject.get("dSpecialization").getAsString();
		String address = docObject.get("dAddress").getAsString();
		String email = docObject.get("dEmail").getAsString();
		String fee = docObject.get("dFee").getAsString();
		String hospital = docObject.get("dWHospital").getAsString();
		
		String output = doc.addDoctors(name, specialization, address, email, fee, hospital);
		
		return output;
	}
	
	@RolesAllowed({ "admin","patient" })
	@GET
	@Path("/readDoctors")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors()
	{
		return doc.readDoctors();
	}

	
	@RolesAllowed({ "admin","doctor" })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String docData)
	{
		JsonObject docObject = new JsonParser().parse(docData).getAsJsonObject();
				//JsonParser().parse(docData).getAsJsonObject();
	
		String id = docObject.get("ID").getAsString();
		String name = docObject.get("dName").getAsString();
		String specialization = docObject.get("dSpecialization").getAsString();
		String address = docObject.get("dAddress").getAsString();
		String email = docObject.get("dEmail").getAsString();
		String fee = docObject.get("dFee").getAsString();
		String hospital = docObject.get("dWHospital").getAsString();
		
		String output = doc.updateDoctor(id, name, specialization, address, email, fee, hospital);
		return output;
		
	}
	
/**
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String docData)
	{
		//Document document = Jsoup.parse(docData, "", Parser.xmlParser());
		org.jsoup.nodes.Document docu = Jsoup.parse(docData, "", Parser.xmlParser()); 
		//docData, "", Parser.xmlParser()
		
		String id = docu.select("ID").text();
		String output = doc.deleteDoctor(id);
		
		return output;
	}
	**/
	
	
	@RolesAllowed("admin")
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String docData)
	{
		JsonObject jsonObject = new JsonParser().parse(docData).getAsJsonObject();
		
		String id = jsonObject.get("ID").getAsString();
		
		String output = doc.deleteDoctor(id);
		
		return output;
	}
	
	@RolesAllowed({"admin","patient"})
	@GET
	@Path("/searchDoc/{dName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String searchDoc(@PathParam("dName") String docData)
	{
		
		return doc.searchDoctors(docData);
	}
	
}
