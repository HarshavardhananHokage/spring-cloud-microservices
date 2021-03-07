package com.harsh.photo.user.api.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.photo.user.api.ui.model.CreateUserInput;
import com.harsh.photo.user.api.ui.model.CreateUserOutput;
import com.harsh.photo.user.api.service.UsersService;
import com.harsh.photo.user.api.shared.UserDTO;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService usersService;
	
	@GetMapping("/status/check")
	public String checkStatus() {
		return "working on port " +env.getProperty("local.server.port") + " with Token: " + env.getProperty("token.hmac512.secret");
	}
	
	@PostMapping(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<CreateUserOutput> createUser(@Valid @RequestBody CreateUserInput user) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		System.out.println("Create User: " + user.toString());
		
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		
		System.out.println("User DTO: " + userDTO.toString());
		userDTO = usersService.createUser(userDTO);
		
		CreateUserOutput userOutput = mapper.map(userDTO, CreateUserOutput.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userOutput);
	}

}
