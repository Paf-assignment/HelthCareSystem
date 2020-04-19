package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;


public class Payment {
	
	String output="";
	
	private Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/helthcaresystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

			// For Testing
			System.out.println("Successfully Connected");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Unsuccessful!!!!");
		}
		return con;

	}
	
	public String addPayment (String nic, String cardno, String amount)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error connecting to the Database";
			}
			
			String query =" INSERT INTO `payments`"
					+ "(`pID`, `nic`, `cardno`, `amount`) "
					+ "VALUES (?,?,?,?)";
				
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(query);
			
			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, nic);
			preparedStatement.setInt(3, Integer.parseInt(cardno));
			preparedStatement.setFloat(4, Float.parseFloat(amount));
			
			
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
	
	
	
	public String readPayment() {
		
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to database";
			}
			
			
			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>NIC</th><th>CardNo</th><th>Amount</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			
			String query="select * from payments";
			Statement statement = con.createStatement();
			ResultSet set = statement.executeQuery(query);
			
			while (set.next()) {
				
				String id = Integer.toString(set.getInt("pID"));
				String nic = set.getString("nic");
				String cardno = set.getString("cardno");
				String amount = set.getString("amount");
				
				
			
				output += "<tr><th>" + nic +"</th>";
				output += "<th>" + cardno + "</th>";
				output += "<th>" + amount + "</th>";
				
				
				output += "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>"
						+ "<td><form method=\"post\" action=\"payment.jsp\">"
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
	
	
	
	public String deletePayment(String id)
	{
		
		
		try {
			
			Connection con = connect();
			
			if(con == null)
			{
				return "Error while connecting to the database";
			}
			
			String query = "delete from payments where pID =?";
			
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

	
	
	
	

	public String updatePayment(String ID, String nic, String cardno, String amount)
	{
		try {
			
			Connection con = connect();
			
			if(con == null) {
			
				return "Error while connecting to the database";
			}
			
			String query = "UPDATE `payments` "
					+ "SET `nic`=?,`cardno`=?,`amount`=?"
					+ " WHERE `pID`= ?";
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, nic);
			preparedStatement.setString(2, cardno);
			preparedStatement.setFloat(3,Float.parseFloat(amount));
			preparedStatement.setInt(4, Integer.parseInt(ID));
			
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
	
	
	
	
	
	
	public String searchPayment(String searchText) {
		
		System.out.println(searchText);
		
		try {
			Connection con = connect();
			
			if(con == null) {
			return "Erroe while connecting to the database";
		}

			output="<table class=\"table\" border =\"1\">"
					+ "<tr><th>NIC</th><th>CardNo</th><th>Amount</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM payments WHERE nic LIKE ?";
			PreparedStatement prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1,searchText);
			
			ResultSet set = prepareStatement.executeQuery();
			
			while (set.next()) {
				
				
				String id = Integer.toString(set.getInt("pID"));
				String nic = set.getString("nic");
				String cardno = set.getString("cardno");
				String amount = set.getString("amount");
				
			
				output += "<tr><th>" + nic +"</th>";
				output += "<th>" + cardno + "</th>";
				output += "<th>" + amount + "</th>";
				
				
				output += "<td><input type=\"button\" name=\"btnUpdate\" value=\"update\"></td>"
						+ "<td><form method=\"post\" action=\"payment.jsp\">"
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
