package com.note.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.note.JpaRepository.UserJpaRepository;
import com.note.entities.User;

@Service
public class UserServiceimpl implements UserService {


    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
       

        user.setPassword(passwordEncoder.encode(user.getPassword()));

    

        return this.userRepository.save(user);
    }

	@Override
	public Optional<User> getUserById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<User> updateUser(User user) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUserExist(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isuserExistByUserName(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
