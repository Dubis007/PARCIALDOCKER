package com.pruebastyt.repository;

import com.pruebastyt.model.BeneficioAsignado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficioAsignadoRepository extends JpaRepository<BeneficioAsignado, Long> {
    List<BeneficioAsignado> findByEstudianteId(Long estudianteId);
    List<BeneficioAsignado> findByEstado(String estado);
    Optional<BeneficioAsignado> findByEstudianteIdAndBeneficioId(Long estudianteId, Long beneficioId);
}