package com.example.demo;

public class Account {

	private String name;

	private String password;

	private boolean activate;

	public Account(String name, String password, boolean activate) {
		this.name = name;
		this.password = password;
		this.activate = activate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

}
