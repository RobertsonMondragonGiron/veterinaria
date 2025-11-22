package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.repository;

import com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}