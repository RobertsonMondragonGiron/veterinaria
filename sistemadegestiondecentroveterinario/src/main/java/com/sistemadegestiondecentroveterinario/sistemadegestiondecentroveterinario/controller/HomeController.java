package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.MascotaService;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.PropietarioService;


@Controller
public class HomeController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PropietarioService propietarioService;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalMascotas", mascotaService.count());
        model.addAttribute("totalPropietarios", propietarioService.count());
        return "index";
    }
}