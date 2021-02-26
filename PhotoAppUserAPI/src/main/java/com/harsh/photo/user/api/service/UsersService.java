package com.harsh.photo.user.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.harsh.photo.user.api.shared.UserDTO;

public interface UsersService extends UserDetailsService {
	
	UserDTO createUser(UserDTO userDetails);
	UserDTO getUserByEmail(String emailID);

}
