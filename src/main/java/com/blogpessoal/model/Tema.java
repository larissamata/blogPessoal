package com.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity // indicação que é uma nova tabela no banco de dados
@Table(name = "tb_temas") // nome da tabela no banco de dados

public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull (message = "O atributo 'descrição' é obrigatório.") //não permite deixar o campo vazio
	private String descricao;
	
	/* Tema = one | Postagem = Many
	 * Fetch Lazy = pesquisa preguiçosa
	 * Cascade = como a tabela relacionada vai se comportar em momentos 
	 * de deletar dados.
	 * 
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema",
			cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("tema")
	private List <Postagem> postagem; // a postagem é uma lista para podermos ter mais de uma postagem pro mesmo tema
	
	
	
	public List<Postagem> getPostagem() {
		return postagem;
	}
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
