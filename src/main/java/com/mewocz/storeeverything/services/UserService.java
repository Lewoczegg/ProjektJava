package com.mewocz.storeeverything.services;


import com.mewocz.storeeverything.dto.UserRequest;
import com.mewocz.storeeverything.model.Role;
import com.mewocz.storeeverything.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserRequest userRequest);

    User findUserByLogin(String login);

    List<User> findAllUsers();

    User findById(Long id);

    List<Role> findRoles();

    void save(User user);

}