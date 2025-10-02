package com.note.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;




@Entity
public class User  implements UserDetails {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	@NotBlank(message = "User Name is Required")
	private String name;
	@NotBlank(message = "email is Required")
	private String email;
	@NotBlank(message = "password is Required")
	private String password;
	
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	 private List<Note> note = new ArrayList<>();


		public int getId() {
		return Id;
	}



	public void setId(int id) {
		Id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public List<Note> getNote() {
		return note;
	}



	public void setNote(List<Note> note) {
		this.note = note;
	}



	public void setPassword(String password) {
		this.password = password;
	}



		public User() {
		super();
		// TODO Auto-generated constructor stub
	}



		public User(int id, @NotBlank(message = "User Name is Required") String name,
			@NotBlank(message = "email is Required") String email,
			@NotBlank(message = "password is Required") String password, List<Note> note) {
		super();
		Id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.note = note;
	}



		@Override
		public String toString() {
			return "User [Id=" + Id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
		}
		
		

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	
	
	 
	 
	 
	
	
	
	
	
	

}
