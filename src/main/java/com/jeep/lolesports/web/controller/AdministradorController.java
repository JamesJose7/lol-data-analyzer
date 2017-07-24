package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.Jugador;
import com.jeep.lolesports.service.IntegranteService;
import com.jeep.lolesports.service.RiotService;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdministradorController {
    @Autowired
    private Environment env;

    @Autowired
    private IntegranteService integranteService;

    @Autowired
    private RiotService riotService;

    @RequestMapping("/admin")
    public String controlPanel(Model model) {
        return "admin/control_panel";
    }

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
        //Save it as a 'Integrante'
        Integrante newIntegrante = new Integrante(jugador, integrante.getCedula(), integrante.getNombreIntegrante(),
                integrante.getCiclo(), integrante.getCarrera(), integrante.getEmail());
        integranteService.save(newIntegrante);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Integrante agregado correctamente!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /
        return "redirect:/";
    }
}
