package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.AdministradorDao;
import com.jeep.lolesports.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    @Autowired
    private AdministradorDao administradorDao;

    @Override
    public Administrador findByUsername(String username) {
        return administradorDao.findByUsername(username);
    }

    @Override
    public void save(Administrador administrador) {
        administradorDao.save(administrador);
    }

    @Override
    public void delete(Administrador administrador) {
        administradorDao.delete(administrador);
    }

    @Override
    public List<Administrador> findAll() {
        return administradorDao.findAll();
    }
}