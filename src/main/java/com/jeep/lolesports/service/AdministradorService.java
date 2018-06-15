package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Administrador;

import java.util.List;

public interface AdministradorService {
    List<Administrador> findAll();
    Administrador findByUsername(String username);
    void save(Administrador administrador);
    void delete(Administrador administrador);
}
