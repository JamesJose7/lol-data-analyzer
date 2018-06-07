package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.AdministradorDao;
import com.jeep.lolesports.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorDao administradorDao;

    @Override
    public void save(Administrador administrador) {
        administradorDao.save(administrador);
    }
}
