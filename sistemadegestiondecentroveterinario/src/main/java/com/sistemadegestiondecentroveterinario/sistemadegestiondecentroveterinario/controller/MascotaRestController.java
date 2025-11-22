package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.controller;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Mascota;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaRestController {

    @Autowired
    private MascotaService mascotaService;


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
    public ResponseEntity<Mascota> createMascota(@Valid @RequestBody Mascota mascota) {
        try {
            Mascota nuevaMascota = mascotaService.save(mascota);
            return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id,
                                                 @Valid @RequestBody Mascota mascota) {
        return mascotaService.findById(id)
                .map(mascotaExistente -> {
                    mascota.setId(id);
                    Mascota mascotaActualizada = mascotaService.save(mascota);
                    return new ResponseEntity<>(mascotaActualizada, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMascota(@PathVariable Long id) {
        try {
            if (mascotaService.existsById(id)) {
                mascotaService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/count")
    public ResponseEntity<Long> countMascotas() {
        long count = mascotaService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}