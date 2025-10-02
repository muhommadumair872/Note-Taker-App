package com.note.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.note.entities.User;

public interface UserJpaRepository   extends JpaRepository<User, Integer> {
	
	
	Optional<User> findByEmail( String email);

    Optional<User> findByEmailAndPassword(String password ,String email);



}
