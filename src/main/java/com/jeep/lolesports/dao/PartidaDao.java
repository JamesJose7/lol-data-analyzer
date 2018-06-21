package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Partida;

import java.util.List;

public interface PartidaDao {
    List<Partida> findAll();
    Partida findById(long id);
    void save(Partida partida);
    List<Partida> findPlayerMatches(long id);
}
