package com.jeep.lolesports.service;

import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.model.Partida.PartidaBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("app.properties")
public class RiotServiceImpl implements RiotService {
    @Autowired
    private Environment env;

    private static final int END_INDEX_PARTIDAS = 20;

    private static final String SUMMONER_V3_URL = "https://la1.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
    private static final String LEAGUE_V3_URL = "https://la1.api.riotgames.com/lol/league/v3/positions/by-summoner/";
    private static final String MATCHLIST_V3_URL = "https://la1.api.riotgames.com/lol/match/v3/matchlists/by-account/";
    private static final String MATCH_V3_URL = "https://la1.api.riotgames.com/lol/match/v3/matches/";

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
        int accountId = docBasic.getInt("accountId");

        //Ranked info
        String urlRanked = String.format("%s%s?api_key=%s", LEAGUE_V3_URL, id, API_KEY);

        String jsonDataRanked = httpRequest.getRequestContents(urlRanked);
        JSONArray docRanked = new JSONArray(jsonDataRanked);

        Jugador jugador = new Jugador(id, nombre, nivel, accountId);

        //Check first league in array
        if (!docRanked.isNull(0)) {
            JSONObject firstLeague = docRanked.getJSONObject(0);

            String tipoColaRanked = firstLeague.getString("queueType");
            if (tipoColaRanked.equals("RANKED_SOLO_5x5"))
                setRankedSolo(firstLeague, jugador);
            else
                setRankedFlex(firstLeague, jugador);
        }

        //Check second league in array
        if (!docRanked.isNull(1)) {
            JSONObject secondLeague = docRanked.getJSONObject(1);

            String tipoColaRanked = secondLeague.getString("queueType");
            if (tipoColaRanked.equals("RANKED_SOLO_5x5"))
                setRankedSolo(secondLeague, jugador);
            else
                setRankedFlex(secondLeague, jugador);
        }

        return jugador;
    }

    private void setRankedSolo(JSONObject rankedSolo, Jugador jugador) {
        jugador.setTipoColaRankedSolo(rankedSolo.getString("queueType"));
        jugador.setVictoriasRankedSolo(rankedSolo.getInt("wins"));
        jugador.setDerrotasRankedSolo(rankedSolo.getInt("losses"));
        jugador.setNivelRankedSolo(rankedSolo.getString("tier"));
        jugador.setRangoRankedSolo(rankedSolo.getString("rank"));
        jugador.setNombreLigaRankedSolo(rankedSolo.getString("leagueName"));
        jugador.setPuntosRankedSolo(rankedSolo.getInt("leaguePoints"));
    }

    private void setRankedFlex(JSONObject rankedFlex, Jugador jugador) {
        jugador.setTipoColaRankedFlex(rankedFlex.getString("queueType"));
        jugador.setVictoriasRankedFlex(rankedFlex.getInt("wins"));
        jugador.setDerrotasRankedFlex(rankedFlex.getInt("losses"));
        jugador.setNivelRankedFlex(rankedFlex.getString("tier"));
        jugador.setRangoRankedFlex(rankedFlex.getString("rank"));
        jugador.setNombreLigaRankedFlex(rankedFlex.getString("leagueName"));
        jugador.setPuntosRankedFlex(rankedFlex.getInt("leaguePoints"));
    }

    @Override
    public List<Partida> getPartidasByAccountId(long accountId) {
        final String API_KEY = env.getProperty("lol.api.key");

        List<Partida> partidas = new ArrayList<>();

        // Build recent matches url
        String recentMatchesUrl = String.format("%s%d?endIndex=%d&api_key=%s",
                MATCHLIST_V3_URL, accountId, END_INDEX_PARTIDAS, API_KEY);

        // Get recent matches list
        HTTPService httpService = new HTTPService();
        String jsonRecentMatches = httpService.getRequestContents(recentMatchesUrl);
        JSONObject recentMatchesDoc = new JSONObject(jsonRecentMatches);
        JSONArray recentMatchesList = recentMatchesDoc.getJSONArray("matches");

        // Iterate through matches getting their details
        for (int i = 0; i < recentMatchesList.length(); i++) {
            //Get Json summary for match
            JSONObject matchSummary = recentMatchesList.getJSONObject(i);
            //Get match id
            long matchId = matchSummary.getLong("gameId");

            // Build match details url
            String matchDetailsUrl = String.format("%s%d?api_key=%s",
                    MATCH_V3_URL, matchId, API_KEY);

            // Get details for each match
            String jsonMatchDetails = httpService.getRequestContents(matchDetailsUrl);
            JSONObject matchDetails = new JSONObject(jsonMatchDetails);

            //Create composite id = matchId + accountId
            long id = Long.parseLong(String.format("%d%d", matchId, accountId));
            Partida partida = new PartidaBuilder(id)
                    .withMatchId(matchId)
                    .withGameDuration(matchDetails.getLong("gameDuration"))
                    .withGameMode(matchDetails.getString("gameMode"))
                    .withGameType(matchDetails.getString("gameType"))
                    .build();
            //Add partida to the the list
            partidas.add(partida);
        }
        return partidas;
    }
}
