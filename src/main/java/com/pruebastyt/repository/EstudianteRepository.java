package com.pruebastyt.repository;

import com.pruebastyt.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByDocumento(String documento);
    Optional<Estudiante> findByCodigo(String codigo);
    Optional<Estudiante> findByUsuarioId(Long usuarioId);
    List<Estudiante> findByFacultadId(Long facultadId);
    boolean existsByDocumento(String documento);
    boolean existsByCodigo(String codigo);
}