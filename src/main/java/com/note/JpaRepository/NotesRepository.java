package com.note.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.note.entities.Note;
import com.note.entities.User;

public interface NotesRepository extends  JpaRepository<Note, Integer>  {
	
	
    List<Note> findByUser(User user);

    
    Optional<Note> findById(Long id);

	
	
	
	
	
	


}
