package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.service.WizardServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wizards")
public class WizardControllerTest {

    @Autowired
    private WizardServiceTest wizardService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("wizard", new Wizard());
        return "wizard/register"; 
    }

    @PostMapping("/register")
    public String registerWizard(@ModelAttribute Wizard wizard, Model model) {
        try {
            wizardService.saveWizard(wizard);
            return "redirect:/wizards/login?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "wizard/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "wizard/login";
    }
}