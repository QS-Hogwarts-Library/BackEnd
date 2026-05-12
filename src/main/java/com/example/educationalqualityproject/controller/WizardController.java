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
}