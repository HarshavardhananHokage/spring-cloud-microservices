package com.harsh.photo.user.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.harsh.photo.user.api.data.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByemailID(String emailID);
	UserEntity findByUserID(String userID);
}
