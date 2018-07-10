package com.jeep.lolesports.utils;

import com.jeep.lolesports.model.matches_data.ParticipantsStatsPar;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String prettyQueueName(String queueType) {
        String pretty;
        switch (queueType) {
            case "RANKED_SOLO_5x5":
                pretty = "Solo Ranked";
                break;
            case "RANKED_FLEX_SR":
                pretty = "Flex Ranked";
                break;
            default:
                pretty = "Unranked";
        }
        return pretty;
    }

    public static String getDurationFromSecs(long duration) {
        double val = (double) duration / 60;
        String[] arr= String.valueOf(val).split("\\.");
        long[] intArr = new long[2];
        intArr[0] = Long.parseLong(arr[0]);
        intArr[1] = Long.parseLong(arr[1]);

        int secs = (int) Math.round(((double) val - intArr[0]) * 60);

        return String.format("%dm %ds", intArr[0], secs);
    }

    public static String firstUpperCase(String string) {
        String firsLetter = string.substring(0,1).toUpperCase();
        String rest = string.substring(1,string.length()).toLowerCase();
        return firsLetter + rest;
    }

    public static String getIconsUrl(int icon) {
        if (icon == 0)
            return "http://via.placeholder.com/150?text=%20";
        else
            return "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/" + icon + ".png";
    }

    public static String calculateKDA(int kills, int deaths, int assists) {
        try {
            if (deaths == 0)
                return "0";
            double kda = (double) (kills + assists) / deaths;
            return String.format("%.2f", kda);
        } catch (ArithmeticException e) {
            return "0";
        }
    }

    public static String getLargestMultiKill(int multikill) {
        switch (multikill) {
            case 0:
                return "-";
            case 1:
                return "-";
            case 2:
                return "Double Kill";
            case 3:
                return "Triple Kill";
            case 4:
                return "Quadra Kill";
            case 5:
                return "Penta Kill";
            case 6:
                return "Hexa Kill";
            default:
                return "-";
        }
    }

    public static List<ParticipantsStatsPar> getTeamById(int id, List<ParticipantsStatsPar> participants) {
        List<ParticipantsStatsPar> selectedTeam = new ArrayList<>();
        for (ParticipantsStatsPar participant : participants)
            if (participant.getTeamId() == id)
                selectedTeam.add(participant);
        return selectedTeam;
    }
}
