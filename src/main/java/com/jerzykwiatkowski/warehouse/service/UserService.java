package com.jerzykwiatkowski.warehouse.service;

import com.jerzykwiatkowski.warehouse.entity.User;

public interface UserService {
    User findByUserName(String name);
    void saveUser(User user);
}
