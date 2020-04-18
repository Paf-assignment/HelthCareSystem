package com;

import javax.annotation.security.RolesAllowed;

//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
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
	
	//@RolesAllowed("admin")
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointments() {
		return appointment.viewAppointments();
	}
	
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addDoctor(String docData)
	{
		JsonObject docObject = new JsonParser().parse(docData).getAsJsonObject();
		
		String date = docObject.get("date").getAsString();
		String time = docObject.get("time").getAsString();
		String doctor = docObject.get("doctor").getAsString();
		String patient = docObject.get("patient").getAsString();
		
		
		String output = appointment.makeAppointment(date, time, doctor, patient);
		
		return output;
	}
	
	
	
	
	
	
	
	
	
	//update appointment
	//@RolesAllowed({ "admin","patient" })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAppointment(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String Aid = itemObject.get("Aid").getAsString();
		String date = itemObject.get("date").getAsString();
		String time = itemObject.get("time").getAsString();
		String doctorid = itemObject.get("doctorid").getAsString();
		String patientnic = itemObject.get("patientnic").getAsString();
		
		String output = appointment.updateAppointment(Aid, date, time, doctorid, patientnic);
		return output;
	}

	/*
	//Cancel appointment
	//@RolesAllowed({ "admin","patient" })
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String cancelAppointment(String Aid) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(Aid, "", Parser.xmlParser());
		// Read the value from the element <aid>
		String appID = doc.select("aid").text();
		String output = appointment.cancelAppointments(appID);
		return output;
	}
	 */
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String docData)
	{
		JsonObject jsonObject = new JsonParser().parse(docData).getAsJsonObject();
		
		String appID = jsonObject.get("appID").getAsString();
		
		String output = appointment.cancelAppointments(appID);
		
		return output;
	}
	
	
	
}
