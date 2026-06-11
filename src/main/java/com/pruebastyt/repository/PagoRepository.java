package com.pruebastyt.repository;

import com.pruebastyt.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByEstudianteId(Long estudianteId);
    List<Pago> findByEstado(String estado);
}