package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Propietario;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    // 1. Listar Propietarios (CRUD: Listar)
    @GetMapping
    public String listarPropietarios(Model model) {
        model.addAttribute("propietarios", propietarioService.findAll());
        return "propietario/lista"; // Esto mapea a la plantilla Thymeleaf: src/main/resources/templates/propietario/lista.html
    }

    // 2. Mostrar formulario para Crear Propietario (CRUD: Crear)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("propietario", new Propietario());
        return "propietario/form"; // Mapea a src/main/resources/templates/propietario/form.html
    }

    // 3. Guardar Propietario (CRUD: Crear/Editar)
    @PostMapping("/guardar")
    public String guardarPropietario(@ModelAttribute("propietario") Propietario propietario) {
        // Aquí se pueden añadir Validaciones básicas
        propietarioService.save(propietario);
        return "redirect:/propietarios?success";
    }

    // 4. Mostrar formulario para Editar Propietario (CRUD: Editar)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        propietarioService.findById(id).ifPresent(propietario -> model.addAttribute("propietario", propietario));
        return "propietario/form";
    }

    // 5. Eliminar Propietario (CRUD: Eliminar)
    @GetMapping("/eliminar/{id}")
    public String eliminarPropietario(@PathVariable Long id) {
        propietarioService.deleteById(id);
        return "redirect:/propietarios?deleted";
    }
}