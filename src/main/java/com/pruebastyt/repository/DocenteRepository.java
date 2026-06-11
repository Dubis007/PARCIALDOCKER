package com.pruebastyt.repository;

import com.pruebastyt.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByDocumento(String documento);
    Optional<Docente> findByUsuarioId(Long usuarioId);
    List<Docente> findByFacultadId(Long facultadId);
}