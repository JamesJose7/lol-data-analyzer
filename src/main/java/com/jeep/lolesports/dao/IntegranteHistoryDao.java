package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.IntegranteHistory;

import java.util.List;

public interface IntegranteHistoryDao {
    List<IntegranteHistory> findAll();
    IntegranteHistory findById(int id);
    List<IntegranteHistory> findBySummonerId(String id);
    IntegranteHistory findByAccountId(int id);
    void save(IntegranteHistory integrante);
    void delete(IntegranteHistory integrante);
}
