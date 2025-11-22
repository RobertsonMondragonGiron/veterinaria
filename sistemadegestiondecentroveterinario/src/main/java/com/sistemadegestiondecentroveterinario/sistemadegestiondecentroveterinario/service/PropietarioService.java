package com.sistemadegestiondecentroveterinario.service;

import com.sistemadegestiondecentroveterinario.model.Propietario;
import com.sistemadegestiondecentroveterinario.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioService {

    @Autowired
    private PropietarioRepository propietarioRepository;


    public List<Propietario> findAll() {
        return propietarioRepository.findAll();
    }


    public Propietario save(Propietario propietario) {
        return propietarioRepository.save(propietario);
    }


    public Optional<Propietario> findById(Long id) {
        return propietarioRepository.findById(id);
    }


    public void deleteById(Long id) {
        propietarioRepository.deleteById(id);
    }
}