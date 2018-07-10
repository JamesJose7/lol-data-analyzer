package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.model.static_riot.Champion;

import java.util.List;

public interface RiotService {
    Jugador getJugadorByName(String jugadorName);
    List<Partida> getPartidasByAccountId(long accountId, int summonerId);
    void loadChampionData();
    Champion getChampionById(int id);
}
