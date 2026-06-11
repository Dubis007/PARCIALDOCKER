package com.pruebastyt.repository;

import com.pruebastyt.model.Coordinador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoordinadorRepository extends JpaRepository<Coordinador, Long> {
    Optional<Coordinador> findByDocumento(String documento);
    Optional<Coordinador> findByUsuarioId(Long usuarioId);
}