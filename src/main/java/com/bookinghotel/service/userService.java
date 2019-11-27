package com.bookinghotel.service;

import com.bookinghotel.model.User;

import java.util.List;

public interface userService {
    void saveOrUpdate(User user);
    List<User> findAllUser();
    User findUserById(int id);
}
