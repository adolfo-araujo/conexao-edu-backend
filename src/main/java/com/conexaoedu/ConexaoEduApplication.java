package com.conexaoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// reduz configuração manual ao iniciar o projeto
@SpringBootApplication
public class ConexaoEduApplication {
    public static void main(String[] args) {
        // ponto único de inicialização da aplicação
        SpringApplication.run(ConexaoEduApplication.class, args);
    }
}