package com.conexaoedu.service;

import com.conexaoedu.model.Curso;
import com.conexaoedu.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// service concentra regras e mantém controller mais limpo
@Service
public class CursoService {

    private final MonitoramentoProducer monitoramentoProducer;
	@Autowired
    private CursoRepository cursoRepository;

	CursoService(MonitoramentoProducer monitoramentoProducer) {
		this.monitoramentoProducer = monitoramentoProducer;
	}

    // consulta direta simplifica listagens comuns
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // falhar cedo evita continuar fluxo com id inválido
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id));
    }

    // ponto central facilita futuras validações
    public Curso criar(Curso curso) {
        // espaço reservado para regras adicionais se surgirem
    	monitoramentoProducer.enviarEvento("Curso criado: " + curso.getNome());
        return cursoRepository.save(curso);
    }

    // atualizar sem recriar preserva identidade do registro
    public Curso atualizar(Long id, Curso cursoDetalhes) {
        Curso curso = buscarPorId(id);
        curso.setNome(cursoDetalhes.getNome());
        curso.setCodigo(cursoDetalhes.getCodigo());
        curso.setDescricao(cursoDetalhes.getDescricao());
        curso.setCargaHoraria(cursoDetalhes.getCargaHoraria());
        // vínculos merecem fluxo próprio para evitar mudanças acidentais
        return cursoRepository.save(curso);
    }

    // validar existência antes de excluir melhora previsibilidade
    public void deletar(Long id) {
        Curso curso = buscarPorId(id);
        cursoRepository.delete(curso);
        monitoramentoProducer.enviarEvento("Curso deletado ID: " + id);
    }
}