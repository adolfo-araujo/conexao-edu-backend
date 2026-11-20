package com.conexaoedu.controller;

import com.conexaoedu.model.Post;
import com.conexaoedu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller rest para gerenciamento de posts
// endpoints exigem autenticação, salvo liberações definidas na segurança
@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    // lista todos os posts
    // get /posts
    @GetMapping
    public List<Post> listarTodos() {
        return postService.listarTodos();
    }

    // busca um post pelo id
    // get /posts/{id}
    @GetMapping("/{id}")
    public Post buscarPorId(@PathVariable Long id) {
        return postService.buscarPorId(id);
    }

    // cria um novo post
    // post /posts
    @PostMapping
    public Post criar(@RequestBody Post post) {
        return postService.criar(post);
    }

    // atualiza um post existente
    // put /posts/{id}
    @PutMapping("/{id}")
    public Post atualizar(@PathVariable Long id, @RequestBody Post postDetalhes) {
        return postService.atualizar(id, postDetalhes);
    }

    // remove um post pelo id
    // delete /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        postService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}