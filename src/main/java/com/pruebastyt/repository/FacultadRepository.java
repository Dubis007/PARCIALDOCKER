package com.pruebastyt.repository;

import com.pruebastyt.model.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {
    Optional<Facultad> findByNombre(String nombre);
}