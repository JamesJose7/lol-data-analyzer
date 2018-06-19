package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.User;

public interface UserDao {
    User findByUsername(String username);
    void save(User user);
    void delete(User user);
    User findById(int id);
}
