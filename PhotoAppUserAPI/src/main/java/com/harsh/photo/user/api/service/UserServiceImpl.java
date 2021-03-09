package com.harsh.photo.user.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.harsh.photo.user.api.data.AlbumsServiceClient;
import com.harsh.photo.user.api.data.UserEntity;
import com.harsh.photo.user.api.repository.UsersRepository;
import com.harsh.photo.user.api.shared.UserDTO;
import com.harsh.photo.user.api.ui.model.AlbumResponseModel;

@Service
public class UserServiceImpl implements UsersService {

	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	//RestTemplate restTemplate;
	AlbumsServiceClient albumsServiceClient;
	Environment env;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AlbumsServiceClient albumsServiceClient, Environment env) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.albumsServiceClient = albumsServiceClient;
		this.env = env;
	}

	@Override
	public UserDTO createUser(UserDTO userDetails) {

		userDetails.setUserID(UUID.randomUUID().toString());

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		System.out.println("User Entity: " + userEntity.toString());

		userEntity = usersRepository.save(userEntity);

		userDetails = mapper.map(userEntity, UserDTO.class);

		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByemailID(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		User user = new User(userEntity.getEmailID(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
		return user;
	}

	@Override
	public UserDTO getUserByEmail(String emailID) {
		UserEntity userEntity = usersRepository.findByemailID(emailID);

		if (userEntity == null)
			throw new UsernameNotFoundException(emailID);

		UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
		return userDTO;
	}

	@Override
	public UserDTO getUserByID(String id) {
		UserEntity userEntity = usersRepository.findByUserID(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
		
		/*String albumsURL = String.format(env.getProperty("albums.url"), id);
		System.out.println("Albums URL: "+albumsURL);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		
		ResponseEntity<List<AlbumResponseModel>> response = restTemplate.exchange(albumsURL, HttpMethod.GET, entity
				, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
		});*/
		
		logger.info("Before calling Albums Microservice");
		userDTO.setAlbums(albumsServiceClient.getAlbums(id));
		logger.info("Before calling Albums Microservice");
		return userDTO;
	}

}
