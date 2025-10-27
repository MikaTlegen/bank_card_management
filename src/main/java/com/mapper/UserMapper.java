package com.mapper;

import com.dto.request.UserRequest;
import com.dto.response.UserResponse;
import com.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setFullName(user.getFullName());
        userResponse.setActive(user.isActive());
        return userResponse;
    }

    public List<UserResponse> toUserResponses(List<User> users) {
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    public User toUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }

        User user = new User();
        user.setId(userRequest.getId());
        user.setPassword(userRequest.getPassword());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user.setFullName(userRequest.getFullName());
        user.setActive(true);
        return user;
    }

    public void updateUserFromRequest(UserRequest userRequest, User user) {
        if (userRequest == null || user == null) {
            return;
        }

        if (userRequest.getUserName() != null) {
            user.setUserName(userRequest.getUserName());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getFullName() != null) {
            user.setFullName(userRequest.getFullName());
        }

    }
}
