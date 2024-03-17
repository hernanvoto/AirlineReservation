package com.airconsole.reservation.model;

import java.io.Serializable;

public class Passenger implements Serializable {
	
	private static final long serialVersionUID = 8601360574720120737L;
	private String name;
	private String surname;
	private String passportNumber;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPassportNumber() {
		return passportNumber;
	}
	
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	} 
}
