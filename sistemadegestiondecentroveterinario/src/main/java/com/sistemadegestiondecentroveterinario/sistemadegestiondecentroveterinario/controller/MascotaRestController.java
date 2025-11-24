package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.dto.MascotaDTO;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Mascota;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Propietario;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.MascotaService;
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
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaRestController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PropietarioService propietarioService;


    @GetMapping
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        List<Mascota> mascotas = mascotaService.findAll();
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        return mascotaService.findById(id)
                .map(mascota -> new ResponseEntity<>(mascota, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<?> createMascota(@Valid @RequestBody MascotaDTO mascotaDTO) {
        try {
            // Buscar el propietario
            Propietario propietario = propietarioService.findById(mascotaDTO.getPropietarioId())
                    .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + mascotaDTO.getPropietarioId()));

            // Crear la mascota
            Mascota mascota = new Mascota();
            mascota.setNombre(mascotaDTO.getNombre());
            mascota.setEspecie(mascotaDTO.getEspecie());
            mascota.setEdad(mascotaDTO.getEdad());
            mascota.setPropietario(propietario);

            // Guardar
            Mascota nuevaMascota = mascotaService.save(mascota);
            return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

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


    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(@PathVariable Long id,
                                           @Valid @RequestBody MascotaDTO mascotaDTO) {
        try {
            // Verificar que la mascota existe
            Mascota mascotaExistente = mascotaService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));

            // Buscar el propietario
            Propietario propietario = propietarioService.findById(mascotaDTO.getPropietarioId())
                    .orElseThrow(() -> new RuntimeException("Propietario no encontrado con ID: " + mascotaDTO.getPropietarioId()));

            // Actualizar los datos
            mascotaExistente.setNombre(mascotaDTO.getNombre());
            mascotaExistente.setEspecie(mascotaDTO.getEspecie());
            mascotaExistente.setEdad(mascotaDTO.getEdad());
            mascotaExistente.setPropietario(propietario);

            // Guardar
            Mascota mascotaActualizada = mascotaService.save(mascotaExistente);
            return new ResponseEntity<>(mascotaActualizada, HttpStatus.OK);

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


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMascota(@PathVariable Long id) {
        try {
            if (mascotaService.existsById(id)) {
                mascotaService.deleteById(id);
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Mascota eliminada exitosamente");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Mascota no encontrada con ID: " + id);
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar la mascota");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countMascotas() {
        long count = mascotaService.count();
        Map<String, Long> response = new HashMap<>();
        response.put("total", count);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
