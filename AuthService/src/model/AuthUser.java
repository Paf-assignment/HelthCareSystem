package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import javax.ws.rs.core.UriBuilder;



public class AuthUser {
	
	String output ="";
	
	public static Connection connect() {

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
	
 	 public static boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
     {
         boolean isAllowed = false;
         String role = "" , user = "", psw = "" ;
         
         try {
        	 
     		Connection con = connect();
     		
     		if (con == null) {
     			
     			System.out.println("Successfully connected...");
     		}
     		// create a prepared statement
     		String query = "SELECT * FROM users where username='"+username+"' and password='"+password+"'";
     		PreparedStatement preparedStmt = con.prepareStatement(query);
     		
     	
     	   	preparedStmt.execute();
     //execute the statement
     		  ResultSet rs = preparedStmt.executeQuery(query);
     		 
     		  if(rs.next()) {
     			  user = rs.getString("username");
     			  psw = rs.getString("password");
     			  role=rs.getString("roll");
     			 
     		  }
     		  con.close();
     		  
     		  
     	} catch (Exception e) {
     				
     				System.err.println(e.getMessage());
     			}
         
         if(username.equals(user) && password.equals(psw))
         {
              
             //Step 2. Verify user role
             if(rolesSet.contains(role))
             {
            	 
                 isAllowed = true;
                 System.out.println(username +" "+password );
             }
         }
         return isAllowed;
         
     }

}
