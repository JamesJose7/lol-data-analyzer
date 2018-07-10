package com.jeep.lolesports.model.static_riot;

import java.util.HashMap;
import java.util.Map;

public class SummonerSpells {
    private static final Map<Integer, String> summonerSpellsMap = createMap();

    private static Map<Integer, String> createMap() {
        Map<Integer,String> map = new HashMap<>();
        map.put(12, "SummonerTeleport");
        map.put(32, "SummonerSnowball");
        map.put(11, "SummonerSmite");
        map.put(31, "SummonerPoroThrow");
        map.put(30, "SummonerPoroRecall");
        map.put(13, "SummonerMana");
        map.put(7, "SummonerHeal");
        map.put(6, "SummonerHaste");
        map.put(4, "SummonerFlash");
        map.put(3, "SummonerExhaust");
        map.put(14, "SummonerDot");
        map.put(1, "SummonerBoost");
        map.put(21, "SummonerBarrier");
        return map;
    }

    public static String getSpellById(int id) {
        for (Map.Entry<Integer, String> entry : summonerSpellsMap.entrySet())
            if (entry.getKey() == id)
                return entry.getValue();
        return "";
    }
}
