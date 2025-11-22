package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.repository;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
}