package com.example.login_system;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.login_system.model.User;
import com.example.login_system.repository.UserRepository;
import com.example.login_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser() {
        // Setup test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        // Mock the repository to return an empty Optional (no user found)
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");

        // Call the method to test
        userService.registerUser(user);

        // Verify that the save method was called once
        verify(userRepository, times(1)).save(user);

        // Check if the password was encoded
        assertEquals("encodedPassword123", user.getPassword());

        // Check if the default role is assigned
        assertEquals("USER", user.getRole());
    }
}
