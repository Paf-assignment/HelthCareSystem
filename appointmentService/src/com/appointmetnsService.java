package com;

//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;


import javax.ws.rs.Path;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.appointments;

@Path("/appointments")
public class appointmetnsService {
	
	/*
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
	return "Hello";
	}
	*/
	
	appointments appointment = new appointments();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointments() {
		return appointment.viewAppointments();
	}
	
	
	
	
	//make appointment 
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String makeAppointment(@FormParam("date") String date, @FormParam("time") String time,
			@FormParam("doctor") String doctor, @FormParam("patient") String patient) {
		String output = appointment.makeAppointment(date, time, doctor, patient);
		return output;
	}
	
	
	
	//update appointment
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppointment(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String Aid = itemObject.get("itemID").getAsString();
		String date = itemObject.get("itemCode").getAsString();
		String time = itemObject.get("itemName").getAsString();
		String doctorid = itemObject.get("itemPrice").getAsString();
		String patientnic = itemObject.get("itemDesc").getAsString();
		
		String output = appointment.updateAppointment(Aid, date, time, doctorid, patientnic);
		return output;
	}

	
	//Cancel appointment
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String cancelAppointment(String Aid) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(Aid, "", Parser.xmlParser());
		// Read the value from the element <itemID>
		String appID = doc.select("itemID").text();
		String output = appointment.cancelAppointments(appID);
		return output;
	}

	
	
	
}
