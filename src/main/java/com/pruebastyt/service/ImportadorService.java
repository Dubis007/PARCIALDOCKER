package com.pruebastyt.service;

import com.pruebastyt.model.Usuario;
import org.springframework.web.multipart.MultipartFile;

public interface ImportadorService {
    void procesarExcel(MultipartFile file, Usuario coordinador);
}