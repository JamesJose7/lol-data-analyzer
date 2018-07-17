package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.IntegranteHistory;
import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.service.IntegranteHistoryService;
import com.jeep.lolesports.service.IntegranteService;
import com.jeep.lolesports.service.PartidaService;
import com.jeep.lolesports.service.RiotService;
import com.jeep.lolesports.utils.extractor.DailyDataExtractor;
import com.jeep.lolesports.utils.extractor.DailyTask;
import com.jeep.lolesports.utils.extractor.ExtractorConfig;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class DailyExtractorController implements DailyTask {
    private DailyDataExtractor mDailyDataExtractor = new DailyDataExtractor(this);
    private static boolean isExecuted = false;

    @Autowired
    private IntegranteService integranteService;
    @Autowired
    private IntegranteHistoryService integranteHistoryService;
    @Autowired
    private PartidaService partidaService;
    @Autowired
    private RiotService riotService;


    @RequestMapping("/admin/extractor")
    public String configForm(Model model) {
        if (!model.containsAttribute("config")) {
            model.addAttribute("config", new ExtractorConfig());
        }

        model.addAttribute("action", "/admin/extractor-update");
        model.addAttribute("heading", "Modificar horario de extracción");
        model.addAttribute("submit", "Actualizar");

        return "admin/extractor_form";
    }

    @RequestMapping(value = "/admin/extractor-update", method = RequestMethod.POST)
    public String modifyDailyExtractor(ExtractorConfig extractorConfig, RedirectAttributes redirectAttributes) {
        // If extractor is running stop it before starting a new one
        if (DailyDataExtractor.isProcessRunning) {
            Thread stopThread = new Thread(() -> mDailyDataExtractor.stop());
            stopThread.start();
        }
        // Run new extractor with new config
        mDailyDataExtractor = new DailyDataExtractor(this);
        mDailyDataExtractor.startExecutionAt(extractorConfig.getHoras(), extractorConfig.getMinutos(), 0);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Extractor modificado correctamente!", FlashMessage.Status.SUCCESS));

        return "redirect:/admin";
    }

    @Override
    public void execute() {
        if (!isExecuted) {
            isExecuted = true;
            System.out.println("-----------------Beginning extraction-------------------------\n\n");
            //Let execution begin again after one minute
            new Thread(() -> {
                try {
                    Thread.sleep(61000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isExecuted = false;
            }).start();

            // Store integrantes data
            List<Integrante> integrantes = integranteService.findAll();
            System.out.println("*Total integrantes: " + integrantes.size());
            for (Integrante integrante : integrantes) {
                IntegranteHistory integranteHistory = new IntegranteHistory(integrante);
                //Get list of current integrante's history
                List<IntegranteHistory> integranteHistories = integranteHistoryService.findBySummonerId(integrante.getId());
                if (integranteHistories.size() > 0) {
                    //Only save if it hasn't been saved in the day
                    Date today = new Date();
                    Date lastSave = integranteHistories.get(integranteHistories.size() -1).getTimestampSaved();
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(today);
                    cal2.setTime(lastSave);
                    boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                    if (!sameDay)
                        integranteHistoryService.save(integranteHistory);
                } else
                    integranteHistoryService.save(integranteHistory);

                System.out.println("\tSaved integrante -> " + integrante.getNombreInvocador());

                // Update player data and save new matches
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
            }
            System.out.println("*History saved successfully");

        }
    }
}
