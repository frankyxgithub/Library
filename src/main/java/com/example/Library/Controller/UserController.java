package com.example.Library.Controller;

import com.example.Library.Model.Users;
import com.example.Library.Repository.UserRepository;
import com.example.Library.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lib/api/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<Users> addUser(@RequestBody @Valid Users user) throws IOException {
        return userService.addUser(user);
    }

    @GetMapping("/users")
    public List<Users> findAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user-id/{userId}")
    public Users findByUserId(@PathVariable Integer userId){
        return userService.findUserByUserId(userId);
    }
    @GetMapping("/users2-email/{email}")
    public ResponseEntity<Users> findUserByEmail(@PathVariable String email){
        Users user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users1-name/{fullName}")
    public ResponseEntity<Users> findUserByFullName(@PathVariable  String fullName) {
        return userService.findUserByFullName(fullName);
    }

    @PutMapping("/users-id/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody Users users) throws IOException {
        Users user = userService.findUserByUserId(userId);
        user.setFullName(users.getFullName());
        user.setEmail(users.getEmail());
        user.setAge(users.getAge());
        user.setAddress(users.getAddress());

        userService.updateUser(userId, users);

        return ResponseEntity.ok("User successfully updated");
    }
    @DeleteMapping("user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) throws IOException {
        return userService.deleteUser(userId);
    }

}
