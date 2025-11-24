package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.dto.PropietarioDTO;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Propietario;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/propietarios")
@CrossOrigin(origins = "*")
public class PropietarioRestController {

    @Autowired
    private PropietarioService propietarioService;

    // GET - Obtener todos los propietarios
    @GetMapping
    public ResponseEntity<List<Propietario>> getAllPropietarios() {
        List<Propietario> propietarios = propietarioService.findAll();
        return new ResponseEntity<>(propietarios, HttpStatus.OK);
    }

    // GET - Obtener propietario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropietarioById(@PathVariable Long id) {
        return propietarioService.findById(id)
                .<ResponseEntity<?>>map(propietario -> new ResponseEntity<>(propietario, HttpStatus.OK))
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Propietario no encontrado con ID: " + id);
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                });
    }

    // POST - Crear nuevo propietario
    @PostMapping
    public ResponseEntity<?> createPropietario(@Valid @RequestBody PropietarioDTO propietarioDTO) {
        try {
            // Crear el propietario desde el DTO
            Propietario propietario = new Propietario();
            propietario.setNombre(propietarioDTO.getNombre());
            propietario.setDocumento(propietarioDTO.getDocumento());
            propietario.setTelefono(propietarioDTO.getTelefono());

            Propietario nuevoPropietario = propietarioService.save(propietario);
            return new ResponseEntity<>(nuevoPropietario, HttpStatus.CREATED);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el propietario: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - Actualizar propietario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePropietario(@PathVariable Long id,
                                               @Valid @RequestBody PropietarioDTO propietarioDTO) {
        try {
            // Verificar que el propietario existe
            Propietario propietarioExistente = propietarioService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + id));

            // Actualizar los datos (manteniendo las mascotas existentes)
            propietarioExistente.setNombre(propietarioDTO.getNombre());
            propietarioExistente.setDocumento(propietarioDTO.getDocumento());
            propietarioExistente.setTelefono(propietarioDTO.getTelefono());

            // Guardar
            Propietario propietarioActualizado = propietarioService.save(propietarioExistente);
            return new ResponseEntity<>(propietarioActualizado, HttpStatus.OK);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Eliminar propietario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropietario(@PathVariable Long id) {
        try {
            if (propietarioService.existsById(id)) {
                propietarioService.deleteById(id);
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Propietario eliminado exitosamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Propietario no encontrado con ID: " + id);
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el propietario: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET - Contar propietarios
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countPropietarios() {
        long count = propietarioService.count();
        Map<String, Long> response = new HashMap<>();
        response.put("total", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET - Obtener mascotas de un propietario
    @GetMapping("/{id}/mascotas")
    public ResponseEntity<?> getMascotasByPropietario(@PathVariable Long id) {
        return propietarioService.findById(id)
                .<ResponseEntity<?>>map(propietario -> new ResponseEntity<>(propietario.getMascotas(), HttpStatus.OK))
                .orElseGet(() -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Propietario no encontrado con ID: " + id);
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                });
    }
}