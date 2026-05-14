package com.example.educationalqualityproject.controller;


import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.educationalqualityproject.entity.Wizard;
import com.example.educationalqualityproject.service.WizardService;



@Controller
@RequestMapping("/wizards")
public class WizardController {

    @Autowired
    private WizardService wizardService;

   @GetMapping
    public String listWizards(
        Model model,
        HttpSession session
        ) 
   {

    Wizard loggedWizard =
            (Wizard) session.getAttribute("loggedWizard");

    if (loggedWizard == null) {
        return "redirect:/login";
    }

    if (!loggedWizard.isAdmin()) {
        return "redirect:/books";
    }

    List<Wizard> wizards =
            wizardService.getAllWizards();

    model.addAttribute("wizards", wizards);

    return "wizard/list";
}

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("wizard", new Wizard());

        return "wizard/register";
    }

    @PostMapping
      public String createWizard(
                @ModelAttribute Wizard wizard,
                Model model)
        {     

    if (wizardService.emailAlreadyExists(wizard.getEmail())) {

        model.addAttribute(
                "error",
                "E-mail já cadastrado!"
        );

        return "wizard/register";
    }

    if (wizard.getEmail().equals("admin@hogwarts.com")) {

     wizard.setAdmin(true);

     } 
     
     else {

        wizard.setAdmin(false);
  }

        wizardService.saveWizard(wizard);

        return "redirect:/login";
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
        Model model,
        HttpSession session
) {

    Wizard loggedWizard =
            (Wizard) session.getAttribute("loggedWizard");

    if (loggedWizard == null) {
        return "redirect:/login";
    }

    if (!loggedWizard.isAdmin()
            && !loggedWizard.getId().equals(id)) {

        return "redirect:/books";
    }

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