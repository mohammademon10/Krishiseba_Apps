package com.krishiseba.krishisebaapp.service;

import com.krishiseba.krishisebaapp.model.User;

public interface UserService {
    User registerNewUser(User user);

    boolean changePassword(String email, String currentPassword, String newPassword);

    // This is the new method signature that was missing
    void updateUserProfile(String email, String fullName, String phone, String address);
}