package com.harsh.photo.user.api.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class CreateUserInput {

	@NotNull
	@Size(min = 2, message = "First name must be at least 2 characters")
	private String firstName;

	@NotNull
	@Size(min = 2, message = "Last name must be at least 2 characters")
	private String lastName;

	@NotNull
	@Size(min = 2, max = 8, message = "Password must be 2 to 8 characters")
	private String password;

	@NotNull
	private String emailID;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	@Override
	public String toString() {
		return "CreateUser [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", emailID="
				+ emailID + "]";
	}

}
