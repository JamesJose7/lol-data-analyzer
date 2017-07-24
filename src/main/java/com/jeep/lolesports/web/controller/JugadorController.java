package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.dao.IntegranteDao;
import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.service.IntegranteService;
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

    @Autowired
    private IntegranteService integranteService;

    // Home page - jugador searcher
    @RequestMapping("/")
    public String listJugadores(Model model) {
        List<Jugador> jugadores = new ArrayList<>();

        Integrante i =  new Integrante(riotJugadorService.getJugadorByName("Zetal"), "Sistemas",
                "123123", 6, "email@sdf.com", "Jose Eduardo");
        Integrante i2 = new Integrante(riotJugadorService.getJugadorByName("ASTANDRE"), "Sistemas",
                "436346", 6, "email@sfsd.com", "Andre Herrera");
        Integrante i3 = new Integrante(riotJugadorService.getJugadorByName("StalinTucoLeon"), "Sistemas",
                "9786986", 6, "esss@sdaf.com", "Stalin Carrion");

        integranteService.save(i);
        integranteService.save(i2);
        integranteService.save(i3);

        //Test
        jugadores.add(i);
        jugadores.add(i2);
        jugadores.add(i3);


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
