package com.conexaoedu.repository;

import com.conexaoedu.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

// herdar jpa evita escrever crud manualmente
public interface CursoRepository extends JpaRepository<Curso, Long> {
    // espaço para consultas personalizadas futuras
}