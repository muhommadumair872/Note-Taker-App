package com.note.Configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.note.Services.SecurityUserDetailsService;


@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private OAuthenticationSuccessHandler handler;
	
	
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		
		return daoAuthenticationProvider;
	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		
		return new BCryptPasswordEncoder();
	}
	
	

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(authorize ->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
});


        http.formLogin(formlogin ->{

        	formlogin
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/dashboard", true)
            .failureUrl("/login?error=true")
            .usernameParameter("email")
            .passwordParameter("password");

        });
        
        
        http.oauth2Login(oauth ->{
        	oauth.loginPage("/login");
        	oauth.successHandler(handler);
        });




        return http.build();
    }

	
	

}
