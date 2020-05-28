package com.ydq.service;

import com.ydq.domain.User;

public interface UserService {
    User getUserById(String userId);
    User updateUserById(User ser);
    void deleteUser(String userId);
}
