package com.note.Form;


import com.note.entities.User;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

  public  class Note {
	

	private int Id;
	private String title;
	private String Contant;
	
	@ManyToOne
	private User user;
	

}
