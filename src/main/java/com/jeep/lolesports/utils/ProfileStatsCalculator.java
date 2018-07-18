package com.jeep.lolesports.utils;

import com.jeep.lolesports.model.IntegranteHistory;
import com.jeep.lolesports.model.Partida;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProfileStatsCalculator {

    public static int[] countPlayedLanes(List<Partida> partidas) {
        /* TOP, JUNGLE, MID, SUPPORT, ADC*/
        int[] lanesPlayed = new int[5];
        for (Partida partida : partidas) {
            String lanePlayed = partida.getPlayerStats().getLane();
            String rolePlayed = partida.getPlayerStats().getRole();
            /*Rito Values:
            MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM */
            switch (lanePlayed) {
                case "MID":
                    lanesPlayed[2]++;
                    break;
                case "MIDDLE":
                    lanesPlayed[2]++;
                    break;
                case "TOP":
                    lanesPlayed[0]++;
                    break;
                case "JUNGLE":
                    lanesPlayed[1]++;
                    break;
                case "BOT":
                    if (rolePlayed.equals("DUO_CARRY"))
                        lanesPlayed[4]++;
                    else if (rolePlayed.equals("DUO_SUPPORT"))
                        lanesPlayed[3]++;
                    break;
                case "BOTTOM":
                    if (rolePlayed.equals("DUO_CARRY"))
                        lanesPlayed[4]++;
                    else if (rolePlayed.equals("DUO_SUPPORT"))
                        lanesPlayed[3]++;
                    break;
                default:
            }
        }
        return lanesPlayed;
    }

    public static int[] countWinLoses(List<Partida> partidas) {
        /* Win, Loss */
        int[] winRatio = new int[2];
        for (Partida partida : partidas) {
            if (partida.isMatchWon())
                winRatio[0]++;
            else
                winRatio[1]++;
        }
        return winRatio;
    }

    public static Map<String[], Integer[]> calculateLevelHistory(List<IntegranteHistory> historyData) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM / d", new Locale("es", "EC"));
        String[] days = new String[historyData.size()];
        Integer[] level = new Integer[historyData.size()];
        int counter = 0;
        for (IntegranteHistory history : historyData) {
            Date timestamp = history.getTimestampSaved();
            days[counter] = dayFormat.format(timestamp);
            level[counter] = history.getNivel();
            counter++;
        }
        Map<String[], Integer[]> stats = new HashMap<>();
        stats.put(days, level);

        return stats;
    }
}
