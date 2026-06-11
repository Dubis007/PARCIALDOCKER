package com.pruebastyt.repository;

import com.pruebastyt.model.ResultadoPrueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultadoPruebaRepository extends JpaRepository<ResultadoPrueba, Long> {
    Optional<ResultadoPrueba> findByEstudianteId(Long estudianteId);
    boolean existsByEstudianteId(Long estudianteId);
}