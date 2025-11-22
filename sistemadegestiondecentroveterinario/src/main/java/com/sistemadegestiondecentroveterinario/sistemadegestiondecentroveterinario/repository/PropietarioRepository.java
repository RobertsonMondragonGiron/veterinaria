package com.sistemadegestiondecentroveterinario.repository;

import com.sistemadegestiondecentroveterinario.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

}