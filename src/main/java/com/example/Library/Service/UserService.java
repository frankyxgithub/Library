package com.example.Library.Service;

import com.example.Library.Controller.LogFile;
import com.example.Library.Exception.UserException;
import com.example.Library.Model.Users;
import com.example.Library.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Cacheable("allUsers")
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @Cacheable(value = "getUserById", key = "#userId")
    public Users findUserByUserId(@PathVariable Integer userId){
        return userRepository.findById(userId).orElseThrow(()-> new UserException("User not found"));
    }

    @Cacheable(value = "findUserByEmail", key = "#email")
    public Users findUserByEmail(@PathVariable String email){
        Users user = (Users) userRepository.findUserByEmail(email).orElseThrow(()-> new UserException("User not found"));

        return ResponseEntity.ok(user).getBody();
    }

    @Cacheable(value = "getByFullName", key = "#fullName")
    public ResponseEntity<Users> findUserByFullName(@PathVariable @Valid String fullName){
        userRepository.findUserByFullName(fullName).orElseThrow(()-> new UserException("User not found"));
        return findUserByFullName(fullName);
    }

    @CacheEvict(value = "AllUsers", allEntries = true)
    public ResponseEntity<Users> addUser(@RequestBody Users users) throws IOException {
        String log = String.format("User with name %s is created successfully\n",users.getFullName());
        LogFile.writeFile(log);
        userRepository.save(users);
        return ResponseEntity.ok(users);
    }

    @CacheEvict(value = "deleteById", key = "#userId", allEntries = true)
    public ResponseEntity<String> deleteUser(@PathVariable @Valid int userId) throws IOException {
        String log = String.format("User with name %s is deleted successfully\n", userId);
        LogFile.writeFile(log);
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User successfully deleted");
    }


    @CacheEvict(value = "updateById", key = "#userId", allEntries = true)
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody Users users) throws IOException {

        Users user = userRepository.findUserByUserId(userId);
        user.setFullName(users.getFullName());
        user.setEmail(users.getEmail());
        user.setAge(users.getAge());
        user.setAddress(users.getAddress());

        userRepository.save(user);

        String log = String.format("User with name %s is updated successfully\n",users.getFullName());
        LogFile.writeFile(log);

        return ResponseEntity.ok("User successfully updated");
    }

}
