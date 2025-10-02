package com.note.entities;

import java.util.Optional;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private String title;
	@Column(length = 1000)
	private String Content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Note(int id, String title, String content, User user) {
		super();
		Id = id;
		this.title = title;
		Content = content;
		this.user = user;
	}

	public Note() {
		super();
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Note [Id=" + Id + ", title=" + title + ", Content=" + Content + "]";
	}

	
	
}
