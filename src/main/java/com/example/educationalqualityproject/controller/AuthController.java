package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.service.WizardService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private WizardService wizardService;

    @GetMapping("/login")
    public String showLoginPage() {

        return "wizard/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String magicRegistration,
            HttpSession session,
            Model model
    ) {

        Wizard wizard =
                wizardService.authenticate(
                        email,
                        magicRegistration
                );

        if (wizard != null) {

            session.setAttribute(
                    "loggedWizard",
                    wizard
            );

            return "redirect:/books";
        }

        model.addAttribute(
                "error",
                "Email ou registro mágico inválido"
        );

        return "wizard/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}