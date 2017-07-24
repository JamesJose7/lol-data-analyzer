package com.jeep.lolesports.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdministradorController {

    @RequestMapping("/admin")
    public String controlPanel(Model model) {
        return "admin/control_panel";
    }

    @RequestMapping("/admin/agregar-integrante")
    public String addIntegrante(Model model) {
        return "admin/integrante_form";
    }
}
