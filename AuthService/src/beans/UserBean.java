package beans;

import java.util.Set;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBean {

	private int id ;
	private String role, username,password;
	
	public UserBean() {
	}
	public int getId() {
		return id;
	}
	public UserBean(int id, String username, String password, String role) {
		this.id = id;
		this.role = role;
		this.username = username;
		this.password = password;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
