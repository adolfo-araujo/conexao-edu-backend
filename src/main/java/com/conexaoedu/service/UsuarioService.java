package com.conexaoedu.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.conexaoedu.model.Usuario;
import com.conexaoedu.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final MonitoramentoProducer monitoramentoProducer;
	private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder, MonitoramentoProducer monitoramentoProducer) {
        this.repo = repo;
        this.encoder = encoder;
		this.monitoramentoProducer = monitoramentoProducer;
    }

    public Usuario register(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email é obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve ter pelo menos 6 caracteres");
        }

        String emailNormalizado = usuario.getEmail().trim().toLowerCase();
        if (repo.findByEmail(emailNormalizado).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado");
        }

        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(emailNormalizado);
        usuario.setSenha(encoder.encode(usuario.getSenha().trim()));
        Usuario salvo = repo.save(usuario);
        monitoramentoProducer.enviarEvento("Novo usuário cadastrado: " + salvo.getEmail());
        return salvo;
    }

    public Usuario login(String email, String senha) {
        if (email == null || senha == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email e senha são obrigatórios");
        }

        Usuario usuario = repo.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos"));

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos");
        }
        monitoramentoProducer.enviarEvento("Usuário realizou login: " + usuario.getEmail());
        return usuario;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
    if (email == null) {
        return Optional.empty();
    }

    return repo.findByEmail(email.trim().toLowerCase());
    }
}
