package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class Doctor {
	
	String output="";
	
	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/helthcaresystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//  spring.datasource.url=jdbc:mysql://localhost:3301/student?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
			// For Testing
			System.out.println("Successfully Connected");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Unsuccessful!!!!");
		}
		return con;

	}
	
	public String addDoctors (String name, String specialization, String address, String email, String fee, String wHospital)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error connecting to the Database";
			}
			
			String query =" INSERT INTO `doctors`"
					+ "(`ID`, `dName`, `dSpecialization`, `dAddress`, `dEmail`, `dFee`, `dWHospital`) "
					+ "VALUES (?,?,?,?,?,?,?)";
				
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(query);
			
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, specialization);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, email);
			preparedStatement.setFloat(6, Float.parseFloat(fee));
			preparedStatement.setString(7, wHospital);
			
			preparedStatement.execute();
			con.close();
			preparedStatement.close();
			
			output="Insert Successfully";
			System.out.println(output);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			output="Error inserting data";
			System.out.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String readDoctors() {
		
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to database";
			}
			
			//ID`, `dName`, `dSpecialization`, `dAddress`, `dEmail`, `dFee`, `dWHospital
			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>Name</th><th>Specialization</th><th>Address</th><th>E-mail</th><th>Charges</th><th>Working Hospital</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query="select * from doctors";
			Statement statement = con.createStatement();
			ResultSet set = statement.executeQuery(query);
			
			while (set.next()) {
				
				String id = Integer.toString(set.getInt("ID"));
				String name = set.getString("dName");
				String specialization = set.getString("dSpecialization");
				String address = set.getString("dAddress");
				String email = set.getString("dEmail");
				String charges = Float.toString(set.getFloat("dFee"));
				String hospitals = set.getString("dWHospital");
				
			
				output += "<tr><th>" + name +"</th>";
				output += "<th>" + specialization + "</th>";
				output += "<th>" + address + "</th>";
				output += "<th>" + email + "</th>";
				output += "<th>" + charges + "</th>";
				output += "<th>" + hospitals + "</th>";
				
				output += "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>"
						+ "<td><form method=\"post\" action=\"doctor.jsp\">"
						+ "<input name=\"btnRemove\" value=\"remove\" type=\"submit\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + id + "\">" + "</form></td></tr>" ;
			}
			
			con.close();
			
			output +="</table>";
			
			statement.close();
			System.out.println("successfully print all data...");
					
			
		} catch (Exception e) {
			// TODO: handle exception
			output = "Cannot read the data";
			System.err.println(e.getMessage());
	
		}
		
		return output;
	}
	
	public String deleteDoctor(String id)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error while connecting to the database";
			}
			
			String query = "delete from doctors where ID =?";
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(id));
			preparedStatement.execute();
			con.close();
			
			output = "Delete Successsfully";
			System.out.println(output);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			output ="Error deleting data";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	public String updateDoctor(String ID, String name, String specialization, String address, String email, String fee, String wHospital)
	{
		try {
			
			Connection con = connect();
			
			if(con == null) {
			
				return "Error while connecting to the database";
			}
			
			String query = "UPDATE `doctors` "
					+ "SET `dName`=?,`dSpecialization`=?,`dAddress`=?,`dEmail`=?,`dFee`=?,`dWHospital`=?"
					+ " WHERE `ID`= ?";
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, specialization);
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, email);
			preparedStatement.setFloat(5, Float.parseFloat(fee));
			preparedStatement.setString(6, wHospital);
			preparedStatement.setInt(7, Integer.parseInt(ID));
			
			preparedStatement.execute();
			con.close();
			preparedStatement.close();
			
			output ="Update successfully";
			System.out.println(output);
			
		} catch (Exception e) {
			// TODO: handle exception
			output =" Error updating data";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
		
	public String searchDoctors(String searchText) {
		
		System.out.println(searchText);
		
		try {
			Connection con = connect();
			
			if(con == null) {
			return "Erroe while connecting to the database";
		}

			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>Name</th><th>Specialization</th><th>Address</th><th>E-mail</th><th>Charges</th><th>Working Hospital</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM doctors WHERE dName LIKE ?";
			PreparedStatement prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1,searchText);
			
			ResultSet set = prepareStatement.executeQuery();
			
			while (set.next()) {
				
				
				String id = Integer.toString(set.getInt("ID"));
				String name = set.getString("dName");
				String specialization = set.getString("dSpecialization");
				String address = set.getString("dAddress");
				String email = set.getString("dEmail");
				String charges = Float.toString(set.getFloat("dFee"));
				String hospitals = set.getString("dWHospital");
				
			
				output += "<tr><th>" + name +"</th>";
				output += "<th>" + specialization + "</th>";
				output += "<th>" + address + "</th>";
				output += "<th>" + email + "</th>";
				output += "<th>" + charges + "</th>";
				output += "<th>" + hospitals + "</th>";
				
				output += "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>"
						+ "<td><form method=\"post\" action=\"doctor.jsp\">"
						+ "<input name=\"btnRemove\" value=\"remove\" type=\"submit\">"
						+ "<input name=\"id\" type=\"hidden\" value=\"" + id + "\">" + "</form></td></tr>" ;
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
}
