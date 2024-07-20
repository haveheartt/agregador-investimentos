package tech.gui.agregadorinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.gui.agregadorinvestimentos.controller.CreateUserDTO;
import tech.gui.agregadorinvestimentos.controller.UpdateUserDTO;
import tech.gui.agregadorinvestimentos.entity.User;
import tech.gui.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("it should create a user with success")
        void shouldCreateAUser() {

            //arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
                    );

            //act
            var output = userService.createUser(input);

            //assert
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("it should thrown an exception when an error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            //arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "123"
            );

            //act & assert
            assertThrows(RuntimeException.class, () ->  userService.createUser(input));

        }

    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("it should get an user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            //arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //act
            var output = userService.getUserById(user.getUserId().toString());

            //assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("it should get an user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {

            //arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //act
            var output = userService.getUserById(userId.toString());

            //assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }

    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("it should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();

            //act
            var output = userService.listUsers();

            //assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }


    @Nested
    class deleteById {

        @Test
        @DisplayName("it should delete user with success when user exists")
        void shouldDeleteUseWithSuccessWhenUserExists() {

            //arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();

            //act
            userService.deleteById(userId.toString());

            //assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }


        @Test
        @DisplayName("it should NOT delete user with success when user NOT exists")
        void shouldNotDeleteUseWithSuccessWhenUserNotExists() {

            //arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();

            //act
            userService.deleteById(userId.toString());

            //assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }

    }

    @Nested
    class updateUserById {

        @Test
        @DisplayName("it should update user by id when user exists and username and password are filled")
        void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordAreFilled() {

            //arrange
            var updateUserDto = new UpdateUserDTO(
                    "newusername",
                    "newpassword"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //act
            userService.updateUserById(user.getUserId().toString(), updateUserDto);

            //assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getUsername(), userCaptured.getUsername());
            assertEquals(user.getPassword(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }
    }
}