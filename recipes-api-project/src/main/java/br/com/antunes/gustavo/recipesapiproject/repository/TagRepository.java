package br.com.antunes.gustavo.recipesapiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.recipesapiproject.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	Tag findByName(String name);

}
