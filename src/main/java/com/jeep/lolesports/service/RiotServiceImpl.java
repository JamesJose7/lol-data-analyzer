package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("app.properties")
public class RiotServiceImpl implements RiotService {
    @Autowired
    private Environment env;

    private static final String SUMMONER_V3_URL = "https://la1.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
    private static final String LEAGUE_V3_URL = "https://la1.api.riotgames.com/lol/league/v3/positions/by-summoner/";

    @Override
    public Jugador getJugadorByName(String nombreJugador) {
        final String API_KEY = env.getProperty("lol.api.key");

        //Build url with api key and name
        String url = String.format("%s%s?api_key=%s", SUMMONER_V3_URL, nombreJugador, API_KEY);

        //Get JSON doc
        HTTPService httpRequest = new HTTPService();
        String jsonDataBasic = httpRequest.getRequestContents(url);

        //Basic info
        JSONObject docBasic = new JSONObject(jsonDataBasic);
        int id = docBasic.getInt("id");
        String nombre = docBasic.getString("name");
        int nivel = docBasic.getInt("summonerLevel");

        //Ranked info
        String urlRanked = String.format("%s%s?api_key=%s", LEAGUE_V3_URL, id, API_KEY);

        String jsonDataRanked = httpRequest.getRequestContents(urlRanked);
        JSONArray docRanked = new JSONArray(jsonDataRanked);

        String tipoColaRanked;
        int victoriasRanked;
        int derrotasRanked;
        String nivelRanked;
        String rangoRanked;
        String nombreLigaRanked;
        int puntosRanked;
        if (!docRanked.isNull(0)) {
            JSONObject firstLeague = docRanked.getJSONObject(0);

            tipoColaRanked = firstLeague.getString("queueType");
            victoriasRanked = firstLeague.getInt("wins");
            derrotasRanked = firstLeague.getInt("losses");
            nivelRanked = firstLeague.getString("tier");
            rangoRanked = firstLeague.getString("rank");
            nombreLigaRanked = firstLeague.getString("leagueName");
            puntosRanked = firstLeague.getInt("leaguePoints");
        } else {
            tipoColaRanked = "UNRANKED";
            victoriasRanked = 0;
            derrotasRanked = 0;
            nivelRanked = "UNRANKED";
            rangoRanked = "";
            nombreLigaRanked = "N/A";
            puntosRanked = 0;
        }

        Jugador jugador = new Jugador(id, nombre, nivel,
                tipoColaRanked, victoriasRanked, derrotasRanked, nivelRanked, rangoRanked, nombreLigaRanked, puntosRanked);
        return jugador;
    }
}
