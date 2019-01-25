package com.org.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

	@Value("${poc.users}")
	private String[] users;

	
	@Value("${poc.passwords}")
	private String[] passwords;


	public String[] getUsers() {
		return users;
	}


	public void setUsers(String[] users) {
		this.users = users;
	}


	public String[] getPasswords() {
		return passwords;
	}


	public void setPasswords(String[] passwords) {
		this.passwords = passwords;
	}



	
}