package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.service;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Propietario;
import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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


    public boolean existsById(Long id) {
        return propietarioRepository.existsById(id);
    }


    public long count() {
        return propietarioRepository.count();
    }
}