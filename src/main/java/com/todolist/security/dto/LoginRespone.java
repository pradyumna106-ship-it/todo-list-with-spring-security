package com.todolist.security.dto;

import java.util.List;

public class LoginRespone {
	private String username;
	private String jwtToken;
	private List<String> roles;
	public LoginRespone() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginRespone(String username, String jwtToken, List<String> roles) {
		super();
		this.username = username;
		this.jwtToken = jwtToken;
		this.roles = roles;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the jwtToken
	 */
	public String getJwtToken() {
		return jwtToken;
	}
	/**
	 * @param jwtToken the jwtToken to set
	 */
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "LoginRespone [username=" + username + ", jwtToken=" + jwtToken + ", roles=" + roles + "]";
	}
	
}
