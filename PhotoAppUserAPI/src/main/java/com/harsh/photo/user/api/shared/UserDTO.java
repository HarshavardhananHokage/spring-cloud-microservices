package com.harsh.photo.user.api.shared;

import java.io.Serializable;
import java.util.List;

import com.harsh.photo.user.api.ui.model.AlbumResponseModel;

public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7759440225440546885L;

	private String firstName;
	private String lastName;
	private String password;
	private String emailID;
	private String userID;
	private String encryptedPassword;
	private List<AlbumResponseModel> albums;

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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", emailID="
				+ emailID + ", userID=" + userID + ", encryptedPassword=" + encryptedPassword + "]";
	}

}
