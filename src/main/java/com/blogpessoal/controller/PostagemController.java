package com.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.model.Postagem;
import com.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens") // é pra aceitar as requisições somente com links final /postagens
@CrossOrigin(allowedHeaders = "*", origins = "*") // de onde vamos aceitar os pedidos (requisições)

public class PostagemController {

	@Autowired // modo de instanciar do spring
	private PostagemRepository postagemRepository;

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());

	}

     @GetMapping("/{id}") // esse id vem do banco de dados
     public ResponseEntity<Postagem> getById(@PathVariable Long id) {
	    return postagemRepository.findById(id)
	    		.map(resposta -> ResponseEntity.ok(resposta)) // tem seta então é lambda e faz com que a gente não precise fazer um if imenso
	    		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}

}
