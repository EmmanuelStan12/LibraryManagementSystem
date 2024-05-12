package com.bytebard.librarymanagementsystem.user.domain;

import com.bytebard.sharespace.user.UserMapper;
import com.bytebard.sharespace.user.data.UserEntity;
import com.bytebard.sharespace.user.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @TestConfiguration
    public static class DefaultConfig {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        String password = passwordEncoder.encode("test.password");
        UserEntity user = new UserEntity(
                1L,
                "test.firstname",
                "test.lastname",
                "test.email",
                password,
                "test.bio",
                "test.username"
        );

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmailOrUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmailOrUsername(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    public void test_findByUsername_existingUser() {
        String username = "test.username";
        User user = userService.findByUsername(username);
        assertNotNull(user);
        assertEquals(user.getUsername(), username);
    }

    @Test
    public void test_findByUsername_nonExistingUser() {
        String username = "test.username.1";
        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(username));
    }

    @Test
    public void test_findByEmail_existingUser() {
        String email = "test.email";
        User user = userService.findByUsername(email);
        assertNotNull(user);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void test_findByEmail_nonExistingUser() {
        String email = "test.email.1";
        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(email));
    }

    @Test
    public void test_register_existingEmail() {
        String password = passwordEncoder.encode("test.password");
        User user = new User(
                null,
                "test.firstname",
                "test.lastname",
                "test.email",
                password,
                "test.bio",
                "test.username"
        );
        Exception exception = assertThrows(Exception.class, () -> userService.register(user));
        assertEquals(exception.getMessage(), "Email already exists");
    }

    @Test
    public void test_register_existingUsername() {
        String password = passwordEncoder.encode("test.password");
        User user = new User(
                null,
                "test.firstname",
                "test.lastname",
                "test.email.1",
                password,
                "test.bio",
                "test.username"
        );
        Exception exception = assertThrows(Exception.class, () -> userService.register(user));
        assertEquals(exception.getMessage(), "Username already exists");
    }

    @Test
    public void test_register_newUser() {
        String password = passwordEncoder.encode("test.password");
        User user = new User(
                2L,
                "test.firstname",
                "test.lastname",
                "test.email1.1",
                password,
                "test.bio",
                "test.username.1"
        );
        UserEntity userEntity = UserMapper.toUserEntity(user);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        User createdUser = assertDoesNotThrow(() -> userService.register(user));
        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertNotEquals("test.password", createdUser.getPassword());
        assertEquals(password, createdUser.getPassword());
    }
}
