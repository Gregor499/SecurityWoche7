package user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public void createNewUser(User newUser) {
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