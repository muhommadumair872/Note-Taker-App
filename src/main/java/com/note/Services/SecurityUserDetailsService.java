package com.note.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.note.JpaRepository.UserJpaRepository;



@Service
public class SecurityUserDetailsService   implements UserDetailsService{
	
	

    @Autowired
    private UserJpaRepository userJpaRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        return this.userJpaRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

	}

}
