package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Administrador;

public interface AdministradorDao {
    Administrador findByUsername(String username);
    void save(Administrador administrador);
}
