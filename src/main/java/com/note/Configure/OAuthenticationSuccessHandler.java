package com.note.Configure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.note.JpaRepository.UserJpaRepository;
import com.note.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private UserJpaRepository userRepository;
	
	
	Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		logger.info("OAuthenticationSuccessHandler");
		
		
		
	      DefaultOAuth2User user  = (DefaultOAuth2User) authentication.getPrincipal();
		
	      
	      logger.info(user.getName());
	      
	      user.getAttributes().forEach((key,value)->{
	    	  logger.info("{} = {}" , key , value);
	      });
		
		logger.info(user.getAuthorities().toString());
		
		
		String email = user.getAttribute("email");
		String name = user.getAttribute("name");
		String password = user.getAttribute("password");
		
		
		User user1 = new User();
		
		user1.setEmail(email);
		user1.setName(name);
		user1.setPassword("khalifaibex");
		
		
		   User user2 = userRepository.findByEmail(email).orElse(null);
		
		   if(user2==null) {
			   
			   userRepository.save(user1);
			   logger.info("User are Saved::::::"+user1.getEmail());
		   }
		
		
		
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
		
	}
	
	
	

}
