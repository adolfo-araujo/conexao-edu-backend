package com.conexaoedu.controller;

import com.conexaoedu.model.Curso;
import com.conexaoedu.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller rest para gerenciamento de cursos
// endpoints exigem autenticação, salvo liberações definidas na segurança
@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // lista todos os cursos
    // get /cursos
    @GetMapping
    public List<Curso> listarTodos() {
        return cursoService.listarTodos();
    }

    // busca um curso pelo id
    // get /cursos/{id}
    @GetMapping("/{id}")
    public Curso buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    // cria um novo curso
    // post /cursos
    @PostMapping
    public Curso criar(@RequestBody Curso curso) {
        return cursoService.criar(curso);
    }

    // atualiza um curso existente
    // put /cursos/{id}
    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso cursoDetalhes) {
        return cursoService.atualizar(id, cursoDetalhes);
    }

    // remove um curso pelo id
    // delete /cursos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}