package DTO;


public class Patient {
	protected String id;
	protected String address;
	protected String first_name;
	protected String last_name;
	protected String email;

	public Patient() {
	}

	public Patient(String id, String address, String first_name, String last_name,String email) {
		this.id = id;
		this.address = address;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setEmail(String email){this.email = email;}
	public String getEmail(){return email; }
}
