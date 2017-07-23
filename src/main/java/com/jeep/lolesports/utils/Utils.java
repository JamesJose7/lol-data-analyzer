package com.jeep.lolesports.utils;

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

    public static String firstUpperCase(String string) {
        String firsLetter = string.substring(0,1).toUpperCase();
        String rest = string.substring(1,string.length()).toLowerCase();
        return firsLetter + rest;
    }
}
