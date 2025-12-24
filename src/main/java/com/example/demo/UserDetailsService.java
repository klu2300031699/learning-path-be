package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository repository;

    // CREATE
    public UserDetails createUser(UserDetails user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }
        return repository.save(user);
    }

    // READ ALL
    public List<UserDetails> getAllUsers() {
        return repository.findAll();
    }

    // READ BY ID
    public UserDetails getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE
    public UserDetails updateUser(Long id, UserDetails updatedUser) {
        UserDetails existingUser = getUserById(id);

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        return repository.save(existingUser);
    }

    // DELETE
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    // LOGIN
    public UserDetails loginUser(String emailOrMobile, String password) {
        UserDetails user = repository.findByEmail(emailOrMobile);
        if (user == null) {
            user = repository.findByMobileNumber(emailOrMobile);
        }
        
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect email/mobile or password");
        }
        
        return user;
    }
}
