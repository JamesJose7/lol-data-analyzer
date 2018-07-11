package com.jeep.lolesports.web.controller;

import com.jeep.lolesports.utils.extractor.DailyDataExtractor;
import com.jeep.lolesports.utils.extractor.DailyTask;
import com.jeep.lolesports.utils.extractor.ExtractorConfig;
import com.jeep.lolesports.web.FlashMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DailyExtractorController implements DailyTask {
    private DailyDataExtractor mDailyDataExtractor = new DailyDataExtractor(this);
    private static boolean isExecuted = false;

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
            System.out.println("-----------------Beginning extraction-------------------------\n\n\n\n");
            //Let execution begin again after one minute
            new Thread(() -> {
                try {
                    Thread.sleep(61000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isExecuted = false;
            }).start();
        }
    }
}
