package com.example.demo.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService myUserService;

    @PostMapping
    public void postNewUser(@RequestBody User newUser) {
        myUserService.createNewUser(newUser);
    }


    @GetMapping
    public String getUsername(Principal principal) {
        return principal.getName();
    }
}

