package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Integrante;

import java.util.List;

public interface IntegranteDao {
    List<Integrante> findAll();
    Integrante findById(String id);
    Integrante findByAccountId(int id);
    void save(Integrante integrante);
    void delete(Integrante integrante);
}
