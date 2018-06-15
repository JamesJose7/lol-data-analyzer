package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.*;
import com.jeep.lolesports.service.AdministradorService;
import com.jeep.lolesports.service.IntegranteService;
import com.jeep.lolesports.service.RiotService;
import com.jeep.lolesports.service.UserService;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdministradorController {
    @Autowired
    private Environment env;

    @Autowired
    private IntegranteService integranteService;
    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private UserService userService;

    @Autowired
    private RiotService riotService;

    @RequestMapping("/admin")
    public String controlPanel(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
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

    @RequestMapping("/admin/editar-integrante")
    public String editIntegrante(Model model) {
        List<Integrante> integrantes = integranteService.findAll();

        model.addAttribute("integrantes", integrantes);
        model.addAttribute("action", "/edit");
        model.addAttribute("button", "edit");
        model.addAttribute("class", "background-color: green;");

        return "admin/integrantes";
    }


    /* Administradores */
    @RequestMapping("/admin/agregar-admin")
    public String addAdminForm(Model model) {
        if (!model.containsAttribute("administrador")) {
            model.addAttribute("administrador", new Administrador());
        }

        model.addAttribute("action", "/administrador");
        model.addAttribute("heading", "Nuevo administrador");
        model.addAttribute("submit", "Agregar");

        return "admin/administrador_form";
    }

    @RequestMapping(value = "/administrador", method = RequestMethod.POST)
    public String addAdmin(@Valid Administrador administrador, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            //Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.administrador", result);

                //Add the integrante if invalid data was received
                redirectAttributes.addFlashAttribute("administrador", administrador);

                //Redirect back to the form
                return "redirect:/admin/agregar-admin";
        }
        //Save user and null the password for admin object
        Role role = new Role(2L, "ROLE_ADMIN");
        User user = new User(administrador.getUsername(), administrador.getPassword(),
                true, role);
        //BCrypt password
        String pwHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(pwHash);
        userService.save(user);
        administrador.setPassword(null);
        administradorService.save(administrador);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Administrador agregado correctamente!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /
        return "redirect:/";
    }
    @RequestMapping("/admin/borrar-administrador")
    public String deleteAdministrador(Model model) {
        List<Administrador> administradores = administradorService.findAll();

        model.addAttribute("administradores", administradores);
        model.addAttribute("action", "/admin/delete-administrador");
        model.addAttribute("button", "delete");

        return "admin/administradores";
    }

    @RequestMapping(value="/admin/delete-administrador", method=RequestMethod.POST)
    public String deleteAdmin(@RequestParam("submit") String adminUsername, RedirectAttributes redirectAttributes) {
        Administrador administrador = administradorService.findByUsername(adminUsername);
        administradorService.delete(administrador);
        // Delete user associated with the admin
        User user = userService.findByUsername(adminUsername);
        userService.delete(user);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Administrador borrado exitosamente", FlashMessage.Status.SUCCESS));

        return "redirect:/admin/borrar-administrador";
    }
}
