package com.jeep.lolesports;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


/**
 * Created by jeeps on 3/2/2017.
 */

@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        /*RestTemplate restTemplate = new RestTemplate();
        String url = "https://lan.api.pvp.net/api/lol/lan/v1.4/summoner/by-name/Aleist3r?api_key=RGAPI-6fbe9e88-622d-479c-b241-9cd017b127cd";
        String json = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject s = jsonObject.getJSONObject("aleist3r");
        int id = s.getInt("id");
        String name = s.getString("name");
        System.out.println(name);*/

    }
}

