
package com.conexaoedu.model;
import jakarta.persistence.*;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;

    public Long getId(){return id;}
    public String getConteudo(){return conteudo;}
    public void setId(Long id){this.id=id;}
    public void setConteudo(String c){this.conteudo=c;}
}
