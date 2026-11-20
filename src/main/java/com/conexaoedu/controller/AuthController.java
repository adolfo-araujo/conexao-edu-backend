package com.conexaoedu.controller;

import com.conexaoedu.model.Usuario;
import com.conexaoedu.security.JwtUtil;
import com.conexaoedu.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService service;
    private final JwtUtil jwt;

    public AuthController(UsuarioService service, JwtUtil jwt) {
        this.service = service;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Usuario usuario) {
        Usuario novoUsuario = service.register(usuario);
        String token = jwt.generate(novoUsuario.getEmail());
        return buildAuthResponse(novoUsuario, token);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario usuario) {
        Usuario usuarioAutenticado = service.login(usuario.getEmail(), usuario.getSenha());
        String token = jwt.generate(usuarioAutenticado.getEmail());
        return buildAuthResponse(usuarioAutenticado, token);
    }

    @GetMapping("/me")
    public Map<String, Object> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token não informado");
        }

        String token = authHeader.substring(7);
        String email = jwt.extractUsername(token);

        Usuario usuario = service.buscarPorEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return buildAuthResponse(usuario, token);
    }

    private Map<String, Object> buildAuthResponse(Usuario usuario, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", Map.of(
                "id", usuario.getId(),
                "nome", usuario.getNome(),
                "email", usuario.getEmail()
        ));
        return response;
    }
}