package com.conexaoedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexaoedu.model.Post;
import com.conexaoedu.repository.PostRepository;

// service centraliza regras e deixa controller mais enxuto
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MonitoramentoProducer monitoramentoProducer;

    // consulta simples aproveita recurso padrão do repositório
    public List<Post> listarTodos() {
        return postRepository.findAll();
    }

    // erro imediato evita operar sobre registro inexistente
    public Post buscarPorId(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado com ID: " + id));
    }

    // ponto único facilita futuras validações
    public Post criar(Post post) {
        Post salvo = postRepository.save(post);

        monitoramentoProducer.enviarEvento("Post criado - ID: " + salvo.getId());

        return salvo;
    }

    // atualizar só o necessário reduz efeitos colaterais
    public Post atualizar(Long id, Post postDetalhes) {
        Post post = buscarPorId(id);

        post.setConteudo(postDetalhes.getConteudo());

        Post atualizado = postRepository.save(post);

        monitoramentoProducer.enviarEvento("Post atualizado - ID: " + atualizado.getId());

        return atualizado;
    }

    // validar existência antes de excluir melhora previsibilidade
    public void deletar(Long id) {
        Post post = buscarPorId(id);

        postRepository.delete(post);

        monitoramentoProducer.enviarEvento("Post deletado - ID: " + id);
    }
}