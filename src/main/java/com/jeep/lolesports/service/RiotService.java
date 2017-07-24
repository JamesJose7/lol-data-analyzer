package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;

public interface RiotService {
    Jugador getJugadorByName(String jugadorName);
}
