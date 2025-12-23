package com.example.demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserDetailsController {

    @Autowired
    private UserDetailsService service;

    // CREATE
    @PostMapping("/create")
    public UserDetails createUser(
            @RequestBody UserRequest request) {

        UserDetails user = new UserDetails();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return service.createUser(user, request.getConfirmPassword());
    }

    // READ ALL
    @GetMapping("/all")
    public List<UserDetails> getAllUsers() {
        return service.getAllUsers();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public UserDetails getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public UserDetails updateUser(
            @PathVariable Long id,
            @RequestBody UserDetails user) {
        return service.updateUser(id, user);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public UserDetails loginUser(@RequestBody LoginRequest request) {
        return service.loginUser(request.getEmailOrMobile(), request.getPassword());
    }
}

// DTO class
class UserRequest {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String confirmPassword;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}

// Login DTO class
class LoginRequest {
    private String emailOrMobile;
    private String password;

    public String getEmailOrMobile() { return emailOrMobile; }
    public void setEmailOrMobile(String emailOrMobile) { this.emailOrMobile = emailOrMobile; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
