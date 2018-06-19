package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Administrador;

import java.util.List;

public interface AdministradorDao {
    Administrador findByUsername(String username);
    void save(Administrador administrador);
    void delete(Administrador administrador);
    List<Administrador> findAll();
    Administrador findById(long id);
}
