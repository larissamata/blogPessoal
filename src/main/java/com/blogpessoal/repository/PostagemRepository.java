package com.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogpessoal.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long>{

}
