package br.com.antunes.gustavo.recipesapiproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.recipesapiproject.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	Tag findByName(String name);
	
	Page<Tag> findByName(String name, Pageable pageable);
	
	Page<Tag> findAll(Pageable pageable);

}
