package com.example.demo.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void postNewUser(@RequestBody User newUser){
        userService.createNewUser(newUser);
    }


    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable String username){
        return ResponseEntity.of(userService.findByName(username));
    }

    @GetMapping("me")
    public UserDTO getLoggedInUser(Principal principal){
        String username = principal.getName();

        return userService
                .findByName(username)
                .orElseThrow();
    }
}