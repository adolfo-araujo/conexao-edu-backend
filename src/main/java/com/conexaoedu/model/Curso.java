package com.conexaoedu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do curso é obrigatório")
    private String nome;

    @NotBlank(message = "Código do curso é obrigatório")
    @Column(unique = true)
    private String codigo;

    @NotBlank(message = "Descrição do curso é obrigatória")
    private String descricao;

    @NotNull(message = "Carga horária é obrigatória")
    @Positive(message = "Carga horária deve ser maior que zero")
    private Integer cargaHoraria;

    @JsonIgnore
    @ManyToMany(mappedBy = "cursos")
    private Set<Usuario> usuarios = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getCursos().add(this);
    }

    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.getCursos().remove(this);
    }
}
