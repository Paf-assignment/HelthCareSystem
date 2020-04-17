package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;

import javax.ws.rs.core.Response;


import beans.UserBean;

import javax.ws.rs.core.Response.Status;


public class AuthModel {
	
	String output ="";
	
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
	
	
	
public Response insertUsers(UserBean user ) {
	
		
		Response response;
		String output = "{\"status\":\"success\"}";
		try {
			Connection con = connect();
			if (con == null) {
				output = "{\"status\":\"Connection faild\"}";
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(output).build();
			}
			// create a prepared statement
			String query = " insert into users"
					+ "(id,username,password,roll)"
					+ " values (?, ?, ?, ?)";

			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, user.getId());
			preparedStmt.setString(2, user.getUsername());
			preparedStmt.setString(3, user.getPassword());
			preparedStmt.setString(4, user.getRole());

//execute the statement
			preparedStmt.execute();
			output = "{\"status\":\"success\"}";
			response = Response.status(Status.CREATED)
			.entity(output).build();
			con.close();
		} catch (Exception e) {
			output = "{\"status\":"+e.getMessage()+"}";
			response=Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity(output).build();
			System.err.println(e.getMessage());
		}
		
		
		return response;
	}

	public List<UserBean> readUsers() {
		
				return	readUsers(0);
	
	}
	
	public UserBean readUserById(int id) {
		List<UserBean> list =readUsers(id);
			if(!list.isEmpty()) {
				return	list.get(0);
			}
			return null;
	}
	public List<UserBean> readUsers(int id ) {
		List<UserBean> userList = new ArrayList<>();
		try {
			Connection con = connect();
			if (con == null) {

				System.out.println("Error While reading from database");
				return userList;
			}
			String query;
			if ( id==0) {
			 query = "select * from users";
			}else {
			 query = "select * from users where id="+id;
			}
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				UserBean user = new UserBean(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("roll"));

				userList.add(user);

			}
			con.close();

		} catch (Exception e) {
			System.out.println("error wihile reading");
			System.err.println(e.getMessage());
		}
		return userList;
	}

	public Response updateUser(UserBean user) {
		String output = "";
		
		Response response;
		try {
			Connection con = connect();
			if (con == null) {
				output = "{\"status\":\"Connection faild\"}";
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(output).build();
			}
// create a prepared statement
			String query = "UPDATE users SET " + " username=? , " + " password=? ," + " roll=? " 
					 + " WHERE id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getRole());
			preparedStmt.setInt(4, user.getId());
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "{\"status\":\"success\"}";
			response = Response.status(Status.ACCEPTED)
			.entity(output).build();
		} catch (Exception e) {
			output = "{\"status\":"+e.getMessage()+"}";
			response=Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity(output).build();
			System.err.println(e.getMessage());
		}
		return response;
	}

	public Response deleteUser(int ID) {
		String output = "";
		Response response;
		try {
			Connection con = connect();
			if (con == null) {
				output = "{\"status\":\"Connection faild\"}";
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(output).build();
			}
// create a prepared statement
			String query = "delete from users where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, ID);
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "{\"status\":\"success\"}";
			response = Response.status(Status.ACCEPTED)
			.entity(output).build();
		} catch (Exception e) {
			output = "{\"status\":"+e.getMessage()+"}";
			response=Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity(output).build();
			System.err.println(e.getMessage());
		}
		return response;
	}

	

}
