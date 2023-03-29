package br.com.antunes.gustavo.recipesapiproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antunes.gustavo.recipesapiproject.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByEmail(String email);
	
}
