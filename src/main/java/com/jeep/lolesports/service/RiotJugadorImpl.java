package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("app.properties")
public class RiotJugadorImpl implements RiotJugador {
    @Autowired
    private Environment env;

    private static final String summonerV3_URL = "https://la1.api.riotgames.com/lol/summoner/v3/summoners/by-name/";

    @Override
    public Jugador getJugadorByName(String nombreJugador) {
        //Build url with api key and name
        String url = String.format("%s%s?api_key=%s", summonerV3_URL, nombreJugador, env.getProperty("lol.api.key"));

        //Get JSON doc
        HTTPRequest httpRequest = new HTTPRequest();
        String jsonData = httpRequest.getRequestContents(url);
        //Build Jugador from JSON
        Jugador jugador;

        JSONObject doc = new JSONObject(jsonData);
        int id = doc.getInt("id");
        String nombre = doc.getString("name");
        int nivel = doc.getInt("summonerLevel");

        jugador = new Jugador(id, nombre, nivel);
        return jugador;
    }
}
