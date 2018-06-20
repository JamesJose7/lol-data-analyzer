package com.jeep.lolesports.config;

import com.jeep.lolesports.model.Role;
import com.jeep.lolesports.model.User;
import com.jeep.lolesports.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupConfig {
    @Autowired
    private UserService mUserService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        // Create (if it doesn't exist) default super user and save it

        //Check if super user exists
        User superUser = mUserService.findByUsername("saitamaOne");
        if (superUser == null) {
            Role role = new Role(2L, "USER_ADMIN");
            superUser = new User("saitamaOne",
                    "$2a$10$1JdTC6rjqaf4Zm5hMWScReSML4b6umMEdb1uAa9RuB9imdHBDpYMm",
                    true, role);
            mUserService.save(superUser);
        }
    }
}
