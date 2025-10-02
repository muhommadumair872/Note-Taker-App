package com.note.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.note.JpaRepository.NotesRepository;
import com.note.JpaRepository.UserJpaRepository;
import com.note.entities.Note;
import com.note.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class User {

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;

	// Global user for all templates in this controller
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		if (principal != null) {
			String email = principal.getName();
			Optional<com.note.entities.User> userOptional = userJpaRepository.findByEmail(email);
			userOptional.ifPresent(user -> model.addAttribute("user", user));
		}
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model , com.note.entities.User  user) {
		System.out.println("This is a User Dashboard Controller");
		 model.addAttribute("user", user);
		return "user/dashboard";
	}

	@RequestMapping("/AddNotes")
	public String AddNotes(Model model) {
		System.out.println("Add Notes here ");
		model.addAttribute("note", new Note()); // important!

		return "user/AddNotes";
	}

	@RequestMapping("/Note_process")
	public String addNote(@ModelAttribute("note") Note note, Principal principal, HttpSession session, Model model) {

		try {
			String email = principal.getName();
			Optional<com.note.entities.User> optionalUser = userJpaRepository.findByEmail(email);

			if (optionalUser.isPresent()) {
				com.note.entities.User user = optionalUser.get();
				note.setUser(user); // Set FK
				notesRepository.save(note);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/AddNotes";
	}

	@GetMapping("/ShowNote")
	public String showAllNotes(Model model, Principal principal) {
		String email = principal.getName();
		Optional<com.note.entities.User> optionalUser = userJpaRepository.findByEmail(email);
         
		
		if (optionalUser.isPresent()) {
			com.note.entities.User user = optionalUser.get();
			List<Note> notes = notesRepository.findByUser(user); // Make sure this method exists in your NotesRepository
			model.addAttribute("notes", notes);
		}

		return "user/ShowNote"; // Should match the name of your HTML file inside templates/user/
	}

	// Show edit form
	@RequestMapping("/edit-note/{id}")
	public String editNote(@PathVariable("id") int id, Model model, Principal principal) {
		Optional<Note> noteOptional = this.notesRepository.findById(id);
		if (noteOptional.isPresent()) {
			Note note = noteOptional.get();

			// Security check: user ka note hai ya nahi
			String userEmail = principal.getName();
			if (note.getUser().getEmail().equals(userEmail)) {
				model.addAttribute("note", note);
				return "user/EditNote";
			}
		}
		return "redirect:/user/ShowNote";
	}

	@RequestMapping("/note/delete/{id}")
	public String deleteNoteById(@PathVariable("id") int id, Principal principal, HttpSession session) {
		Optional<Note> noteOptional = this.notesRepository.findById(id);

		if (noteOptional.isPresent()) {
			Note note = noteOptional.get();

			// Security check: only allow the owner to delete the note
			String loggedInUserEmail = principal.getName();
			if (note.getUser().getEmail().equals(loggedInUserEmail)) {
				notesRepository.delete(note);
				session.setAttribute("message", "Note deleted successfully.");
			} else {
				session.setAttribute("message", "You are not authorized to delete this note.");
			}
		} else {
			session.setAttribute("message", "Note not found.");
		}

		return "redirect:/user/ShowNote";
	}

	// NoteController.java

	@GetMapping("/note/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model, Principal principal) {
		Optional<Note> optionalNote = notesRepository.findById(id);
		if (optionalNote.isPresent()) {
			Note note = optionalNote.get();

			// Optional: Check if the logged-in user owns this note
			String username = principal.getName();
			if (note.getUser().getEmail().equals(username)) {
				model.addAttribute("note", note);
				return "user/update_note"; // Thymeleaf file name
			} else {
				return "redirect:/user/notes?unauthorized"; // Optional: unauthorized access
			}
		} else {
			return "redirect:/user/notes?notfound";
		}
	}

	@PostMapping("/note/update/{id}")
	public String processUpdateForm(@ModelAttribute("note") Note updatedNote, @PathVariable("id") Long id,
			Principal principal) {
		Optional<Note> optionalNote = notesRepository.findById(id);
		if (optionalNote.isPresent()) {
			Note existingNote = optionalNote.get();

			// Optional: Check ownership again
			if (!existingNote.getUser().getEmail().equals(principal.getName())) {
				return "redirect:/user/notes?unauthorized";
			}

			existingNote.setTitle(updatedNote.getTitle());
			existingNote.setContent(updatedNote.getContent());
			notesRepository.save(existingNote);

			return "redirect:/user/notes?updated";
		} else {
			return "redirect:/user/notes?notfound";
		}
	}

}
