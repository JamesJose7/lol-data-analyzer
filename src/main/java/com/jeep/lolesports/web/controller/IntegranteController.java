package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.service.IntegranteService;
import com.jeep.lolesports.service.RiotService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IntegranteController {
    @Autowired
    private Environment env;

    @Autowired
    private RiotService mRiotServiceService;

    @Autowired
    private IntegranteService integranteService;

    // Home page - jugador searcher
    @RequestMapping("/")
    public String listJugadores(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();

        model.addAttribute("jugadores", jugadores);
        return "jugador/index";
    }

    @RequestMapping("/jugadores/search")
    public String searchJugadores(@RequestParam String q, Model model) {
        Jugador jugador = mRiotServiceService.getJugadorByName(q);

        model.addAttribute("jugador", jugador);
        return "jugador/search";
    }
}
