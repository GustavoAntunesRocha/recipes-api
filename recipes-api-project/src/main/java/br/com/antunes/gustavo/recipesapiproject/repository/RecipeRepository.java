package br.com.antunes.gustavo.recipesapiproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.recipesapiproject.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
	
	Page<Recipe> findAll(Pageable pageable);
	
	Page<Recipe> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
