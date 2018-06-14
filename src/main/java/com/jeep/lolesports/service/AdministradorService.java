package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Administrador;

public interface AdministradorService {
    Administrador findByUsername(String username);
    void save(Administrador administrador);
}
