package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.model.Partida;

import java.util.List;

public interface RiotService {
    Jugador getJugadorByName(String jugadorName);
    List<Partida> getPartidasByAccountId(long accountId);
}
