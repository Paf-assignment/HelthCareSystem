package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class appointments {
	
	
	String output="";
	
	//connection
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/helthcaresystem", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	//make appointment
	public String makeAppointment(String date, String time, String doctor, String patient) {
		String output = "";
		String status="pending";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into appointment (`Aid`,`date`,`time`,`checkeddStatus`,`doctorid`,`patientnic`)"
					+ " values (?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, time);
			preparedStmt.setString(4, status);
			preparedStmt.setInt(5, Integer.parseInt(doctor));
			preparedStmt.setString(6, patient);

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Appointment added successfully!!";

		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());

		}

		return output;

	}
	
	
	
	
	
	//View appointments
	public String viewAppointments() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table  class=\"table\" border=\"1\"><tr><th>Appointment ID</th>"
					+ "<th>date</th><th>Time</th>" + "<th>status</th><th>doctor</th><th>patientnic</th>"+ "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from appointment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String aid = Integer.toString(rs.getInt("Aid"));
				String date = rs.getString("date");
				String time = rs.getString("time");
				String status = rs.getString("checkeddStatus");
				String doctor = rs.getString("doctorid");
				String patient = rs.getString("patientnic");
				// Add into the html table
				output += "<tr><td>" + aid + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + time + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + doctor + "</td>";
				output += "<td>" + patient + "</td>";
				// buttons

				output += "<td><input name=\"btnUpdate\" "
						+ " type=\"button\" value=\"Update\"  class=\"btn btn-warning\"  ></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">" + "<input name=\"btnRemove\" "
						+ " type=\"submit\" value=\"Remove\"  class=\"btn btn-danger\"     >"
						+ "<input name=\"itemID\" type=\"hidden\" " + " value=\"" + aid + "\">"
						+ "</form></td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while View the Appointment.";
			System.err.println(e.getMessage());
		}

		return output;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//update appointments
	public String updateAppointment(String Aid, String date, String time , String doctorid, String 	patientnic ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE appointment SET date=?,time=?,doctorid=?,patientnic=?    WHERE Aid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, time);
			preparedStmt.setString(3, doctorid);
			preparedStmt.setString(4,patientnic);
			preparedStmt.setInt(5,Integer.parseInt(Aid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Appointment";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//cancel appointments
	public String cancelAppointments(String Aid) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting the database for deleting";
			}

			// create a prepared statement
			String query = "delete from appointment where Aid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Aid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while cancelling the Appointment ";
			System.err.println(e.getMessage());
		}

		return output;

	}
	
	
	
	
	
	
//search appointments	
public String searchAppointments(String searchText) {
		
		System.out.println(searchText);
		
		try {
			Connection con = connect();
			
			if(con == null) {
			return "Erroe while connecting to the database";
		}

			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>AID</th><th>Date</th><th>time</th><th>status</th><th>doctorid</th>paientNIC</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM appointment WHERE patientnic LIKE ?";
			PreparedStatement prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1,searchText);
			
			ResultSet set = prepareStatement.executeQuery();
			
			while (set.next()) {
				
				
				String AID = Integer.toString(set.getInt("Aid"));
				String Date = set.getString("date");
				String time = set.getString("time");
				String status = set.getString("checkeddStatus");
				String doctorid = set.getString("doctorid");
				String paientNIC = set.getString("patientnic");
			
				
			
				output += "<tr><th>" + AID +"</th>";
				output += "<th>" + Date + "</th>";
				output += "<th>" + time + "</th>";
				output += "<th>" + status + "</th>";
				output += "<th>" + doctorid + "</th>";
				output += "<th>" + paientNIC + "</th>";
				
				output += "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>"
						+ "<td><form method=\"post\" action=\"doctor.jsp\">"
						+ "<input name=\"btnRemove\" value=\"remove\" type=\"submit\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + AID + "\">" + "</form></td></tr>" ;
			}
			
			con.close();
			prepareStatement.close();

			output += "</table>";
			System.out.println("successfully print search data...");

		} catch (Exception e) {

			output = "Cannot read the data";
			System.err.println(e.getMessage());
				
		}
		return output;	
		
	}


//confirm appointments
public String ConfirmAppointment(String Aid) {
	String output = "";
	String msg="Confirmed";
	try {
		Connection con = connect();
		if (con == null) {
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE appointment SET checkeddStatus=?    WHERE Aid=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, msg);
		preparedStmt.setString(2, Aid);

		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "appointment confirmed successfully";
	} catch (Exception e) {
		output = "Error while confirm  the Appointment";
		System.err.println(e.getMessage());
	}
	return output;
	
	
	
	
}



	
	
	

}
