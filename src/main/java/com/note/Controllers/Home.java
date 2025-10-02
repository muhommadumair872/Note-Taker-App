package com.note.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.note.JpaRepository.UserJpaRepository;
import com.note.entities.User;
import com.note.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Home {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserJpaRepository userJpaRepository;

    // Home Page
    @RequestMapping("/")
    public String home() {
        System.out.println("This is Home page");
        return "index";
    }

    // Signup Page
    @RequestMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Form Submission
    @PostMapping("/process")
    public String processForm(
        @Valid @ModelAttribute("user") User user,
        BindingResult result,
        HttpSession session,
        Model model
    ) {
        try {
            // Validation error handling
            if (result.hasErrors()) {
                StringBuilder errors = new StringBuilder();
                result.getFieldErrors().forEach(error -> {
                    errors.append(error.getDefaultMessage()).append("<br>");
                });

                session.setAttribute("message", new Message("alert-danger", errors.toString()));
                model.addAttribute("user", user); // preserve entered values
                return "signup";
            }

            // Save user
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            
            
            User savedUser = userJpaRepository.save(user);
            System.out.println("Saved user: " + savedUser);

            // Success message
            Message message = new Message("alert-success", "Registration Successfully");
            session.setAttribute("message", message);

            return "redirect:/signup";

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute(
                "message",
                new Message("alert-danger", "Something went wrong! " + e.getMessage())
            );
            return "redirect:/signup";
        }
    }
    
    
    @RequestMapping("/login")
    public String Login_page() {
    	
    	return "login";
    }
    
    
    
    
    
    
}
