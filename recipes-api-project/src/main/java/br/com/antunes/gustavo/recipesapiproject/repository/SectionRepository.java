package br.com.antunes.gustavo.recipesapiproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.recipesapiproject.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Integer>{

}
