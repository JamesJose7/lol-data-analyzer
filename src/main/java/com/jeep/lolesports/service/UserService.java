package com.jeep.lolesports.service;

import com.jeep.lolesports.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    void save(User user);
}
