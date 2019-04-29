package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.model.Administrador;
import com.jeep.lolesports.model.Role;
import com.jeep.lolesports.model.User;
import com.jeep.lolesports.service.AdministradorService;
import com.jeep.lolesports.service.UserService;
import com.jeep.lolesports.utils.extractor.DailyDataExtractor;
import com.jeep.lolesports.utils.extractor.ExtractorConfig;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdministradorController {
    @Autowired
    private Environment env;

    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private UserService userService;

    @RequestMapping("/admin")
    public String controlPanel(Model model) {
        model.addAttribute("extractorState", DailyDataExtractor.isProcessRunning);

        // Currently set extraction hour
        ExtractorConfig extractorConfig =
                new ExtractorConfig(DailyDataExtractor.extractorConfig.getHoras(),
                        DailyDataExtractor.extractorConfig.getMinutos());
        model.addAttribute("hours", extractorConfig.getHoras());
        model.addAttribute("minutes", extractorConfig.getMinutos());

        /*System.out.println(String.format("%dh%d\n%b",
                extractorConfig.getHoras(), extractorConfig.getMinutos(), DailyDataExtractor.isProcessRunning));*/

        return "admin/control_panel";
    }

    /* Administradores */
    @RequestMapping("/admin/agregar-admin")
    public String addAdminForm(Model model) {
        if (!model.containsAttribute("administrador")) {
            model.addAttribute("administrador", new Administrador());
        }

        model.addAttribute("action", "/administrador");
        model.addAttribute("heading", "Nuevo usuario");
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
        Role role = null;
        if (administrador.getRole().equals("ROLE_ADMIN"))
            role = new Role(2L, administrador.getRole());
        else
            role = new Role(1L, administrador.getRole());
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
        return "redirect:/admin";
    }

    @RequestMapping("/admin/edit-admins")
    public String deleteAdministrador(Model model) {
        List<Administrador> administradores = administradorService.findAll();

        model.addAttribute("administradores", administradores);
        model.addAttribute("action", "/admin/delete-administrador");
        model.addAttribute("buttonDelete", "delete");
        model.addAttribute("buttonEdit", "edit");

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

        return "redirect:/admin/edit-admins";
    }

    @RequestMapping(value="admin/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable("id") long id, Model model){
        Administrador administrador = administradorService.findById(id);
        model.addAttribute("action", "/actualizar");
        model.addAttribute("submit", "Actualizar");
        model.addAttribute("administrador", administrador);

        return "admin/administrador_form";
    }

    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public String actualizar(@Valid Administrador administrador, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            //Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.administrador", bindingResult);

            //Add the integrante if invalid data was received
            redirectAttributes.addFlashAttribute("administrador", administrador);

            //Redirect back to the form
            return "redirect:/admin/editar/" + administrador.getId();
        }
        //Get previous admin
        Administrador previousAdmin = administradorService.findById(administrador.getId());
        //Save user and null the password for admin object
        User user = userService.findByUsername(previousAdmin.getUsername());
        //BCrypt password
        String pwHash = BCrypt.hashpw(administrador.getPassword(), BCrypt.gensalt(10));
        user.setUsername(administrador.getUsername());
        user.setPassword(pwHash);
        userService.save(user);
        administrador.setPassword(null);
        administradorService.save(administrador);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Administrador Actualizado correctamente!", FlashMessage.Status.SUCCESS));

        // Redirect browser to /

        return "redirect:/admin/edit-admins";
    }


    @RequestMapping("/user")
    public String displayUserInfo(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Administrador user  = administradorService.findByUsername(principal.getName());
        return principal.getName();
    }


}
