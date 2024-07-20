package tech.gui.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.gui.agregadorinvestimentos.controller.CreateUserDTO;
import tech.gui.agregadorinvestimentos.entity.User;
import tech.gui.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                Instant.now(),
                null
        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }
}
