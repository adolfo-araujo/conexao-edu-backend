package com.conexaoedu.security;

import com.conexaoedu.model.Usuario;
import com.conexaoedu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// adapta usuários do sistema ao formato esperado pelo spring security
// buscar por email acompanha a escolha usada no login
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // centralizar essa conversão evita lógica duplicada na autenticação
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // sem permissões extras simplifica a estrutura inicial
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.emptyList()
        );
    }
}