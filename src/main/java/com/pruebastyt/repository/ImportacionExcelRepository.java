package com.pruebastyt.repository;

import com.pruebastyt.model.ImportacionExcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportacionExcelRepository extends JpaRepository<ImportacionExcel, Long> {
    List<ImportacionExcel> findAllByOrderByFechaImportacionDesc();
}