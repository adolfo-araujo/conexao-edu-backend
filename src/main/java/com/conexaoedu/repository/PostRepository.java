package com.conexaoedu.repository;

import com.conexaoedu.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// herdar jpa reduz código repetitivo de persistência
public interface PostRepository extends JpaRepository<Post, Long> {
    // espaço para consultas personalizadas futuras
}