package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Partida;

import java.util.List;

public interface PartidaService {
    List<Partida> findAll();
    Partida findById(Long id);
    void save(Partida partida);
    List<Partida> findPlayerMatches(long id);
}
