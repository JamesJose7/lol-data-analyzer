package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.model.matches_data.ParticipantsStatsPar;
import com.jeep.lolesports.model.static_riot.Champion;
import com.jeep.lolesports.service.IntegranteService;
import com.jeep.lolesports.service.MatchDataService;
import com.jeep.lolesports.service.PartidaService;
import com.jeep.lolesports.service.RiotService;
import com.jeep.lolesports.utils.ProfileStatsCalculator;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@SuppressWarnings("Duplicates")
@Controller
public class IntegranteController {
    @Autowired
    private Environment env;

    @Autowired
    private IntegranteService integranteService;
    @Autowired
    private PartidaService partidaService;
    @Autowired
    private MatchDataService matchDataService;

    @Autowired
    private RiotService riotService;

    // Home page - jugador searcher
    @RequestMapping("/")
    public String listJugadores(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();

        /*int id = jugadores.get(0).getId();
        List<Partida> partidas = partidaService.findPlayerMatches(jugadores.get(0));
        System.out.println(partidas);*/

        model.addAttribute("jugadores", jugadores);
        return "jugador/index";
    }

    @RequestMapping("/jugadores/{id}")
    public String listPlayerProfile(@PathVariable int id, Model model) {
        // Load champions
        riotService.loadChampionData();
        // Get player by id
        Integrante integrante = integranteService.findById(id);

        // Get player matches
        List<Partida> partidas = partidaService.findPlayerMatches(integrante);
        //Partida partida = partidas.get(0);
        for (Partida partida : partidas) {
            partida.setParticipantsStats(matchDataService.findParticipantsStats(partida));
            for (ParticipantsStatsPar stats : partida.getParticipantsStats()) {
                Champion champ = riotService.getChampionById(stats.getChampionId());
                stats.setChampion(champ);
                //Set player stats
                if (stats.getSummonerId() == id)
                    partida.setPlayerStats(stats);
            }
            partida.setTeams(matchDataService.findTeamStats(partida));
            //Set champion played
            partida.setChampionPlayed(riotService.getChampionById(partida.getChampionPlayedId()));
        }
        //Reverse partidas list
        Collections.reverse(partidas);

        model.addAttribute("jugador", integrante);
        model.addAttribute("partidas", partidas);
        model.addAttribute("playerLanesCount", ProfileStatsCalculator.countPlayedLanes(partidas));
        model.addAttribute("winRatio", ProfileStatsCalculator.countWinLoses(partidas));

        return "jugador/profile";
    }

    @RequestMapping("/jugadores/search")
    public String searchJugadores(@RequestParam String q, Model model) {
        Jugador jugador = riotService.getJugadorByName(q);

        Jugador miembro = null;
        if (jugador == null) {
            model.addAttribute("found", false);
        } else {
            miembro = integranteService.findById(jugador.getId());
            model.addAttribute("found", true);
        }

       if(miembro == null)
           model.addAttribute("miembro", false);
       else
           model.addAttribute("miembro", true);

       model.addAttribute("jugador", jugador);

        return "jugador/search";
    }

    /* Admin control */
    @RequestMapping("/admin/agregar-integrante")
    public String integranteForm(Model model) {
        if (!model.containsAttribute("integrante")) {
            model.addAttribute("integrante", new Integrante());
        }

        model.addAttribute("action", "/integrantes");
        model.addAttribute("heading", "Nuevo integrante");
        model.addAttribute("submit", "Agregar");

        return "admin/integrante_form";
    }

    @RequestMapping(value = "/integrantes", method = RequestMethod.POST)
    public String addIntegrante(@Valid Integrante integrante, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            //Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.integrante", result);

            //Add the integrante if invalid data was received
            redirectAttributes.addFlashAttribute("integrante", integrante);

            //Redirect back to the form
            return "redirect:/admin/agregar-integrante";
        }
        //Get the details of the player from Riot
        Jugador jugador = riotService.getJugadorByName(integrante.getNombreInvocador());
        //Get the recent matches from player
        List<Partida> partidas = riotService.getPartidasByAccountId(jugador.getAccountId(), jugador.getId());
        //Save it as a 'Integrante'
        Integrante newIntegrante = new Integrante(jugador, integrante.getCedula(), integrante.getNombreIntegrante(),
                integrante.getCiclo(), integrante.getCarrera(), integrante.getEmail());
        //newIntegrante.setPartidas(partidas);
        integranteService.save(newIntegrante);
        //Save partidas
        for (Partida partida : partidas) {
            partida.setIntegrante(newIntegrante);
            partidaService.save(partida);
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Integrante agregado correctamente!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /
        return "redirect:/";
    }

    @RequestMapping("/admin/borrar-integrante")
    public String deleteIntegrante(Model model) {
        List<Integrante> integrantes = integranteService.findAll();

        model.addAttribute("integrantes", integrantes);
        model.addAttribute("action", "/delete");
        model.addAttribute("button", "delete");

        return "admin/integrantes";
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String deleteIntegrante(@RequestParam("submit") int integranteId, RedirectAttributes redirectAttributes) {
        Integrante integrante = integranteService.findById(integranteId);
        integranteService.delete(integrante);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Integrante borrado exitosamente", FlashMessage.Status.SUCCESS));

        // TODO: Redirect browser to /categories
        return "redirect:/admin/borrar-integrante";
    }

    @RequestMapping(value = "/admin/editar-integrante")
    public String editIntegrante( Model model) {
        List<Integrante> integrantes = integranteService.findAll();
        model.addAttribute("integrantes", integrantes);
        model.addAttribute("action", "/admin/editar-integrante/");
        model.addAttribute("button", "edit");
        model.addAttribute("class", "background-color: green;");

        return "admin/integrantes_list";
    }

    @RequestMapping(value="/admin/editar-integrante/{id}", method = RequestMethod.GET)
    public String editarIntegrante(@PathVariable("id") int id, Model model){
        Integrante integrante = integranteService.findById(id);
        model.addAttribute("action", "/admin/actualizar-integrante");
        model.addAttribute("submit", "Actualizar");
        model.addAttribute("integrante", integrante);
//        return "admin/integrante_update";
        return "admin/integrante_formx";
    }

    @RequestMapping(value = "/admin/actualizar-integrante", method = RequestMethod.POST)
    public String actualizar(@Valid Integrante integrante, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            //Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.integrante", bindingResult);

            //Add the integrante if invalid data was received
            redirectAttributes.addFlashAttribute("integrante", integrante);

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Error!", FlashMessage.Status.FAILURE));
            //Redirect back to the form
            return "redirect:/admin/editar-integrante/" + integrante.getId();
        }
        Integrante oldIntegrante = integranteService.findById(integrante.getId());

        Jugador objJugador = new Jugador(oldIntegrante.getId(), oldIntegrante.getNombreInvocador(),oldIntegrante.getNivel(), oldIntegrante.getAccountId(),
                oldIntegrante.getTipoColaRankedSolo(),oldIntegrante.getVictoriasRankedSolo(), oldIntegrante.getDerrotasRankedSolo(),
                oldIntegrante.getNivelRankedSolo(), oldIntegrante.getRangoRankedSolo(), oldIntegrante.getNombreLigaRankedSolo(),
                oldIntegrante.getPuntosRankedSolo(), oldIntegrante.getTipoColaRankedFlex(), oldIntegrante.getVictoriasRankedFlex(),
                oldIntegrante.getDerrotasRankedFlex(), oldIntegrante.getNivelRankedFlex(), oldIntegrante.getRangoRankedFlex(),
                oldIntegrante.getNombreLigaRankedFlex(), oldIntegrante.getPuntosRankedFlex());

        Integrante newIntegrante = new Integrante(objJugador,integrante.getCedula(),integrante.getNombreIntegrante(),
                integrante.getCiclo(),integrante.getCarrera(), integrante.getEmail());
        integranteService.save(newIntegrante);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Integrante Actualizado correctamente!", FlashMessage.Status.SUCCESS));


        return "redirect:/admin/editar-integrante/";
    }

    /*LeaderBoard*/
    @RequestMapping("/leaderboard")
    public String listJugadoresLeaderBoard(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        model.addAttribute("jugadores", jugadores);
        model.addAttribute("filterName", "Inicio");
        return "jugador/leaderboard";
    }

    @RequestMapping("/leaderboard/nivel")
    public String listJugadoresLeaderBoardNivel(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        List<Integrante> jugadoresByLvl = new ArrayList<Integrante>();
        int size = jugadores.size();
        //Ordenando jugadores por nivel
        for (int i = 0; i < size; i++){
            Integrante maxIntegrante = jugadores
                    .stream()
                    .max(Comparator.comparing(Integrante::getNivel))
                    .orElseThrow(NoSuchElementException::new);
            jugadores.remove(maxIntegrante);
            jugadoresByLvl.add(maxIntegrante);
        }

        model.addAttribute("jugadores", jugadoresByLvl);
        model.addAttribute("filterName", "Nivel");
        return "jugador/leaderboard";
    }

    @RequestMapping("/leaderboard/victorias-ranked-solo")
    public String listJugadoresLeaderBoardRankedSolo(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        List<Integrante> jugadoresByLvl = new ArrayList<Integrante>();
        int size = jugadores.size();
        //Ordenando jugadores por nivel
        for (int i = 0; i < size; i++){
            Integrante maxIntegrante = jugadores
                    .stream()
                    .max(Comparator.comparing(Integrante::getVictoriasRankedSolo))
                    .orElseThrow(NoSuchElementException::new);
            jugadores.remove(maxIntegrante);
            jugadoresByLvl.add(maxIntegrante);
        }
        model.addAttribute("jugadores", jugadoresByLvl);
        model.addAttribute("filterName", "Victorias (Ranked Solo)");
        return "jugador/leaderboard";
    }

    @RequestMapping("/leaderboard/victorias-ranked-flex")
    public String listJugadoresLeaderBoardRankedFlex(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        List<Integrante> jugadoresByLvl = new ArrayList<Integrante>();
        int size = jugadores.size();
        //Ordenando jugadores por nivel
        for (int i = 0; i < size; i++){
            Integrante maxIntegrante = jugadores
                    .stream()
                    .max(Comparator.comparing(Integrante::getVictoriasRankedFlex))
                    .orElseThrow(NoSuchElementException::new);
            jugadores.remove(maxIntegrante);
            jugadoresByLvl.add(maxIntegrante);
        }
        model.addAttribute("jugadores", jugadoresByLvl);
        model.addAttribute("filterName", "Victorias (Ranked Flex)");
        return "jugador/leaderboardflex";
    }

    @RequestMapping("/leaderboard/rango-ranked")
    public String listJugadoresLeaderBoardRangoRanked(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        List<Integrante> jugadoresUnranked = new ArrayList<Integrante>();
        List<Integrante> jugadoresBronze = new ArrayList<Integrante>();
        List<Integrante> jugadoresSilver = new ArrayList<Integrante>();
        List<Integrante> jugadoresGold = new ArrayList<Integrante>();
        List<Integrante> jugadoresDiamond = new ArrayList<Integrante>();
        List<Integrante> jugadoresPlatinum = new ArrayList<Integrante>();
        List<Integrante> jugadoresChallenger = new ArrayList<Integrante>();
        List<Integrante> jugadoresByRango = new ArrayList<Integrante>();
        //TODO ordenar por rango y nivel
        for(Integrante jugador :jugadores){
            String rango = jugador.getNivelRankedSolo();
            if(rango.equals("UNRANKED")){
            jugadoresUnranked.add(jugador);
            }
            if(rango.equals("BRONZE")){
                jugadoresBronze.add(jugador);
            }
            if(rango.equals("SILVER")){
                jugadoresSilver.add(jugador);
            }
            if(rango.equals("GOLD")){
                jugadoresGold.add(jugador);
            }
            if(rango.equals("PLATINUM")){
                jugadoresPlatinum.add(jugador);
            }
            if(rango.equals("DIAMOND")){
                jugadoresDiamond.add(jugador);
            }
            if(rango.equals("CHALLENGER")){
                jugadoresChallenger.add(jugador);
            }
        }
        jugadoresByRango.addAll(jugadoresChallenger);
        jugadoresByRango.addAll(jugadoresDiamond);
        jugadoresByRango.addAll(jugadoresPlatinum);
        jugadoresByRango.addAll(jugadoresGold);
        jugadoresByRango.addAll(jugadoresSilver);
        jugadoresByRango.addAll(jugadoresBronze);
        jugadoresByRango.addAll(jugadoresUnranked);
        model.addAttribute("jugadores", jugadoresByRango);
        model.addAttribute("filterName", "Rango Solo");
        return "jugador/leaderboard";
    }

    @RequestMapping("/leaderboard/rango-ranked-flex")
    public String listJugadoresLeaderBoardRangoRankedFlex(Model model) {
        //Get all integrantes
        List<Integrante> jugadores = integranteService.findAll();
        List<Integrante> jugadoresUnranked = new ArrayList<Integrante>();
        List<Integrante> jugadoresBronze = new ArrayList<Integrante>();
        List<Integrante> jugadoresSilver = new ArrayList<Integrante>();
        List<Integrante> jugadoresGold = new ArrayList<Integrante>();
        List<Integrante> jugadoresDiamond = new ArrayList<Integrante>();
        List<Integrante> jugadoresPlatinum = new ArrayList<Integrante>();
        List<Integrante> jugadoresChallenger = new ArrayList<Integrante>();
        List<Integrante> jugadoresByRango = new ArrayList<Integrante>();
        //TODO ordenar por rango y nivel
        for(Integrante jugador :jugadores){
            String rango = jugador.getNivelRankedFlex();
            if(rango.equals("UNRANKED")){
                jugadoresUnranked.add(jugador);
            }
            if(rango.equals("BRONZE")){
                jugadoresBronze.add(jugador);
            }
            if(rango.equals("SILVER")){
                jugadoresSilver.add(jugador);
            }
            if(rango.equals("GOLD")){
                jugadoresGold.add(jugador);
            }
            if(rango.equals("PLATINUM")){
                jugadoresPlatinum.add(jugador);
            }
            if(rango.equals("DIAMOND")){
                jugadoresDiamond.add(jugador);
            }
            if(rango.equals("CHALLENGER")){
                jugadoresChallenger.add(jugador);
            }
        }
        jugadoresByRango.addAll(jugadoresChallenger);
        jugadoresByRango.addAll(jugadoresDiamond);
        jugadoresByRango.addAll(jugadoresPlatinum);
        jugadoresByRango.addAll(jugadoresGold);
        jugadoresByRango.addAll(jugadoresSilver);
        jugadoresByRango.addAll(jugadoresBronze);
        jugadoresByRango.addAll(jugadoresUnranked);
        model.addAttribute("jugadores", jugadoresByRango);
        model.addAttribute("filterName", "Rango Flex");
        return "jugador/leaderboardflex";
    }
}
