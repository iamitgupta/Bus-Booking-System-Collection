package com.dev.bbs.beans;

public class Admin {
	private int adminId;
	private String password;
	private String name;
	private String email;
	private long contact;
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", password=" + password + ", name=" + name + ", email=" + email
				+ ", contact=" + contact + "]";
	}
	

	

}
