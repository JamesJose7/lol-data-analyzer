package com.jeep.lolesports.service;

import com.jeep.lolesports.model.IntegranteHistory;

import java.util.List;

public interface IntegranteHistoryService {
    List<IntegranteHistory> findAll();
    IntegranteHistory findById(int id);
    IntegranteHistory findByAccountId(int id);
    void save(IntegranteHistory integrante);
    void delete(IntegranteHistory integrante);
}
