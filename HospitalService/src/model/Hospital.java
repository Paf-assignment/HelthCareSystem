package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {
	
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
	
	public String addHospitals (String name, String no, String address, String email)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error connecting to the Database";
			}
			
			String query = "INSERT INTO `hospital`(`hospitalID`, `hospitalName`, `hospitalphoneNo`, `hospitalAddress`, `hospitalEmail`) "
					+ "VALUES (?,?,?,?,?)";
				
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(query);
			
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, Integer.parseInt(no));
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, email);
			
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
	
	public String readHospital() {
		
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to database";
			}
			
			//hospitalID`, `hospitalName`, `hospitalphoneNo`, `hospitalAddress`, `hospitalEmail
			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>Name</th><th>Phone Number</th><th>Address</th><th>E-mail</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query="select * from hospital";
			Statement statement = con.createStatement();
			ResultSet set = statement.executeQuery(query);
			
			while (set.next()) {
				
				String id = Integer.toString(set.getInt("hospitalID"));
				String name = set.getString("hospitalName");
				String no = Integer.toString(set.getInt("hospitalphoneNo"));
				String address = set.getString("hospitalAddress");
				String email = set.getString("hospitalEmail");
				
			
				output += "<tr><th>" + name +"</th>";
				output += "<th>" + no + "</th>";
				output += "<th>" + address + "</th>";
				output += "<th>" + email + "</th>";
				
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
	
	public String deleteHospital(String id)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error while connecting to the database";
			}
			
			String query = "delete from hospital where hospitalID =?";
			
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

	public String updateDoctor(String ID, String name, String no, String address, String email)
	{
		try {
			
			Connection con = connect();
			
			if(con == null) {
			
				return "Error while connecting to the database";
			}
			
			String query = "UPDATE `hospital` "
							+ "SET `hospitalName`=? ,`hospitalphoneNo`=? ,`hospitalAddress`=? ,`hospitalEmail`=? "
							+ "WHERE `hospitalID`= ?";
					
					
					/*"UPDATE `hospital` "
					+ "SET `dName`=?,`dSpecialization`=?,`dAddress`=?,`dEmail`=?,`dFee`=?,`dWHospital`=?"
					+ " WHERE `ID`= ?";*/
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, Integer.parseInt(no));
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, email);
			preparedStatement.setInt(5, Integer.parseInt(ID));
			
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
		
	public String searchHospital(String searchText) {
		
		System.out.println(searchText);
		
		try {
			Connection con = connect();
			
			if(con == null) {
			return "Erroe while connecting to the database";
		}

			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>Name</th><th>Phone number</th><th>Address</th><th>E-mail</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM hospital WHERE hospitalName LIKE ?";
			PreparedStatement prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1,searchText);
			
			ResultSet set = prepareStatement.executeQuery();
			
			while (set.next()) {
				
				
				String id = Integer.toString(set.getInt("hospitalID"));
				String name = set.getString("hospitalName");
				String no = Integer.toString(set.getInt("hospitalphoneNo"));
				String address = set.getString("hospitalAddress");
				String email = set.getString("hospitalEmail");
				
			
				output += "<tr><th>" + name +"</th>";
				output += "<th>" + no + "</th>";
				output += "<th>" + address + "</th>";
				output += "<th>" + email + "</th>";
				
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



