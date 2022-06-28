package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    public void createNewUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
    }

    public Optional<UserDTO> findByName(String username) {
        Optional<User> optUser = userRepo.findByUsername(username);
        if (optUser.isPresent()){
            return Optional.of(new UserDTO(optUser.get().getUsername()));
        }
        return Optional.empty();
    }
}