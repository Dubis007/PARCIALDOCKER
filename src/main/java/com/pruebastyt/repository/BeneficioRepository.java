package com.pruebastyt.repository;

import com.pruebastyt.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
    Optional<Beneficio> findByNombre(String nombre);
}