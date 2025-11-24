package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Mascota;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.MascotaService;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PropietarioService propietarioService;


    @GetMapping
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.findAll());
        return "mascota/lista";
    }


    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("propietarios", propietarioService.findAll());
        return "mascota/form";
    }


    @PostMapping("/guardar")
    public String guardarMascota(@Valid @ModelAttribute("mascota") Mascota mascota,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            model.addAttribute("propietarios", propietarioService.findAll());
            return "mascota/form";
        }

        mascotaService.save(mascota);
        return "redirect:/mascotas?success";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        mascotaService.findById(id).ifPresent(mascota -> {
            model.addAttribute("mascota", mascota);
            model.addAttribute("propietarios", propietarioService.findAll());
        });
        return "mascota/form";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        mascotaService.deleteById(id);
        return "redirect:/mascotas?deleted";
    }


    @GetMapping("/ver/{id}")
    public String verMascota(@PathVariable Long id, Model model) {
        mascotaService.findById(id).ifPresent(mascota ->
                model.addAttribute("mascota", mascota)
        );
        return "mascota/detalle";
    }
}