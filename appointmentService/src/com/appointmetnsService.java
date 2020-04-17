package com;

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
	public String readItems() {
		return appointment.viewAppointments();
	}
	
	
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String makeAppointment(@FormParam("date") String date, @FormParam("time") String time,
			@FormParam("doctor") String doctor, @FormParam("patient") String patient) {
		String output = appointment.makeAppointment(date, time, doctor, patient);
		return output;
	}
	
	
	

	
	
}
