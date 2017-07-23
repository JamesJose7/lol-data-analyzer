package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.service.RiotJugador;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JugadorController {
    @Autowired
    private Environment env;

    @Autowired
    private RiotJugador riotJugadorService;

    // Home page - jugador searcher
    @RequestMapping("/")
    public String listJugadores(Model model) {
        List<Jugador> jugadores = new ArrayList<>();
        //Test
        jugadores.add(riotJugadorService.getJugadorByName("Zetal"));
        jugadores.add(riotJugadorService.getJugadorByName("ASTANDRE"));
        jugadores.add(riotJugadorService.getJugadorByName("StalinTucoLeon"));

        model.addAttribute("jugadores", jugadores);
        return "jugador/index";
    }

    @RequestMapping("/jugadores/search")
    public String searchJugadores(@RequestParam String q, Model model) {
        Jugador jugador = riotJugadorService.getJugadorByName(q);

        model.addAttribute("jugador", jugador);
        return "jugador/search";
    }
}
