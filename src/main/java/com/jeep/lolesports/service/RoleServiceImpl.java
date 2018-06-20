package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.RoleDao;
import com.jeep.lolesports.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
