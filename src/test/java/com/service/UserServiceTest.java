package com.service;

import com.dto.request.UserRequest;
import com.dto.response.UserResponse;
import com.entity.User;
import com.enums.UserRole;
import com.exception.user.UserNotFoundException;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private User testUserRoleAdmin;
    private UserRequest testUserRequest;
    private UserPrincipal testUserPrincipal;
    private UserPrincipal testUserPrincipalRoleAdmin;
    private UserRequest createUserRequest;


    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUserName("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setFullName("Test User");
        testUser.setRole(UserRole.USER);

        testUserRoleAdmin = new User();
        testUserRoleAdmin.setId(2L);
        testUserRoleAdmin.setUserName("testUser2");
        testUserRoleAdmin.setEmail("test2@example.com");
        testUserRoleAdmin.setPassword("password2");
        testUserRoleAdmin.setFullName("Test User2");
        testUserRoleAdmin.setRole(UserRole.ADMIN);


        testUserRequest = new UserRequest(
                1L,
                "testUser",
                "test@example.com",
                "password",
                "Test User",
                UserRole.ADMIN
        );

        testUserPrincipal = new UserPrincipal(
                testUser
        );

        testUserPrincipalRoleAdmin = new UserPrincipal(
                testUserRoleAdmin
        );

        createUserRequest = new UserRequest(
                null,
                "newUser",
                "newuser@example.com",
                "password123",
                "New User",
                UserRole.USER
        );
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toUserResponse(testUser)).thenReturn(new UserResponse());

        UserResponse userResponse = userService.getUserById(1L);

        assertNotNull(userResponse);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }


//    @Test
//    void testCreateUser_Success() {
//        when(userRepository.existsByEmail(any())).thenReturn(false); // Email не занят
//        when(userMapper.toUser(any(UserRequest.class))).thenReturn(testUser);
//        when(passwordEncoder.encode(any())).thenReturn("encodedPassword"); // Шифруем пароль
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//        when(userMapper.toUserResponse(any(User.class))).thenReturn(new UserResponse());
//
//        // ВЫПОЛНЕНИЕ
//        UserResponse result = userService.createUsers(createUserRequest, testUserPrincipalRoleAdmin);
//
//        // ПРОВЕРКА
//        assertNotNull(result);
//        verify(userRepository).save(any(User.class)); // Проверяем сохранение
//        verify(passwordEncoder).encode("password"); // Проверяем шифрование пароля
//    }
}
