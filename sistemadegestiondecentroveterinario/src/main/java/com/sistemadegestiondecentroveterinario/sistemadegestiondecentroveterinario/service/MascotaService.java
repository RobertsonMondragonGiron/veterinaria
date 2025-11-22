package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Mascota;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;


    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }


    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }


    public Optional<Mascota> findById(Long id) {
        return mascotaRepository.findById(id);
    }


    public void deleteById(Long id) {
        mascotaRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return mascotaRepository.existsById(id);
    }


    public long count() {
        return mascotaRepository.count();
    }
}