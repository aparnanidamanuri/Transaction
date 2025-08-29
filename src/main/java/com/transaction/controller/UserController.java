package com.transaction.controller;

import com.transaction.dto.UserDTO;
import com.transaction.entity.UserEntity;
import com.transaction.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUsers")
    public ResponseEntity<Map<String, Object>> addUsers(@RequestBody @Valid List<@Valid UserDTO> userList) {
        Map<String, Object> response = userService.addUsers(userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchUsers")
    public ResponseEntity<Object> searchUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp registeredAt
    ) {
        List<UserEntity> users = userService.searchUsers(userId, name, email, registeredAt);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "No records found"));
        }
        return ResponseEntity.ok(Collections.singletonMap("users", users));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserEntity> updateUser(@RequestBody @Valid UserDTO userDTO, @RequestParam Integer id) {
        return ResponseEntity.ok(userService.updateUser(userDTO, id));
    }

    @DeleteMapping("/deleteUsers")
    public ResponseEntity<String> deleteAllUsers() {
        return ResponseEntity.ok(userService.deleteAll());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

//    @GetMapping("/manualTrigger")
//    public Map<String, Object> scheduler(){
//        return userService.reportUserCount("Postman Triggered");
//    }


}
