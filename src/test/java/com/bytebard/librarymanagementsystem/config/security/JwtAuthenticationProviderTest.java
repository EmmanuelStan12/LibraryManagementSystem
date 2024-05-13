package com.bytebard.librarymanagementsystem.config.security;

import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtAuthenticationProviderTest {

    @MockBean
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationTokenProvider provider;

    @Test
    public void test_authenticate_throwsBadCredentialsException() {
        createTestUser();
        Assertions.assertThrows(BadCredentialsException.class, () -> provider.authenticate("test.username", "test.password.1"));
    }

    @Test
    public void test_authenticate() {
        createTestUser();
        Assertions.assertDoesNotThrow(() -> provider.authenticate("test.username", "test.password"));
    }

    private void createTestUser() {
        String password = passwordEncoder.encode("test.password");
        User user = new User(
                1L,
                "test.firstname",
                "test.lastname",
                "test.email",
                password,
                "test.username"
        );

        Mockito.when(userService.findByUsername("test.username")).thenReturn(user);
    }
}
