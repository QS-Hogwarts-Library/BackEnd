package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.service.WizardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wizards")
public class WizardController {

    @Autowired
    private WizardService wizardService;

    @GetMapping
    public String listWizards(Model model) {

        List<Wizard> wizards = wizardService.getAllWizards();

        model.addAttribute("wizards", wizards);

        return "wizard/list";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("wizard", new Wizard());

        return "wizard/register";
    }

    @PostMapping
    public String createWizard(@ModelAttribute Wizard wizard) {

        wizardService.saveWizard(wizard);

        return "redirect:/wizards";
    }

    @GetMapping("/{id}")
    public String getWizardById(
            @PathVariable String id,
            Model model
    ) {

        Wizard wizard = wizardService.getWizardById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Bruxo não encontrado: " + id
                        )
                );

        model.addAttribute("wizard", wizard);

        return "wizard/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable String id,
            Model model
    ) {

        Wizard wizard = wizardService.getWizardById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Bruxo não encontrado: " + id
                        )
                );

        model.addAttribute("wizard", wizard);

        return "wizard/edit";
    }

    @PostMapping("/update/{id}")
    public String updateWizard(
            @PathVariable String id,
            @ModelAttribute Wizard wizard
    ) {

        wizard.setId(id);

        wizardService.saveWizard(wizard);

        return "redirect:/wizards";
    }

    @GetMapping("/delete/{id}")
    public String deleteWizard(@PathVariable String id) {

        wizardService.deleteWizard(id);

        return "redirect:/wizards";
    }
}