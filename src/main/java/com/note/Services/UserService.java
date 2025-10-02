package com.note.Services;

import java.util.List;
import java.util.Optional;

import com.note.entities.User;

public interface UserService {


    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isuserExistByUserName(String email);

    List<User> getAllUsers();

}
