package com.service;

import com.dto.request.UserRequest;
import com.dto.response.UserResponse;
import com.entity.User;
import com.exception.user.*;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.toUserResponse(user);

    }

    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    public UserResponse createUsers(UserRequest userRequest, UserPrincipal userPrincipal) {

        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException(userRequest.getEmail());
        }

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User sevedUser = userRepository.save(user);
        return userMapper.toUserResponse(sevedUser);
    }

    public UserResponse updateUsers(Long userId, UserRequest userRequest, UserPrincipal userPrincipal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (userRequest.getUserName() != null && !userRequest.getUserName().equals(user.getUserName())
                && userRepository.existsByUserName(userRequest.getUserName())) {
            throw new UsernameAlreadyExistsException(userRequest.getUserName());
        }
        if (userRequest.getEmail() != null && !userRequest.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException(userRequest.getEmail());
        }

        userMapper.updateUserFromRequest(userRequest, user);

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Transactional
    public void deleteUsers(Long userId, UserPrincipal userPrincipal) {
        User user = userRepository.findUserWithCardsById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getId().equals(userPrincipal.getId())) {
            throw new SelfDeletionForbiddenException();
        }

        if (!user.getCards().isEmpty()) {
            throw new UserHasActiveCardsException();
        }
        user.setDeleted(true);
        userRepository.save(user);
    }

}
