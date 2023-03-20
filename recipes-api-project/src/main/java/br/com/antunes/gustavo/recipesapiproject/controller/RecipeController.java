package br.com.antunes.gustavo.recipesapiproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.recipesapiproject.dto.RecipeDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Recipe;
import br.com.antunes.gustavo.recipesapiproject.exception.ApiErrorResponse;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.service.RecipeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;
	
	@GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("id") int id) {
        RecipeDto recipeDto = recipeService.mapToDto(recipeService.getRecipeById(id));
        return ResponseEntity.ok(recipeDto);
    }
	
	@GetMapping
    public ResponseEntity<Map<String, Object>> getAllTags(
    		@RequestParam (required = false) String name,
    		@RequestParam (defaultValue = "0") int page, 
    		@RequestParam (defaultValue = "3") int size
    		) {
    	List<Recipe> recipes = new ArrayList<>();
    	Pageable paging = PageRequest.of(page, size);
    	Page<Recipe> pageRecipe;
    	if(name == null) {
    		pageRecipe = recipeService.getAllRecipes(paging);
    	} else {
    		pageRecipe = recipeService.searchRecipeByName(name, paging);
    	}
    	recipes = pageRecipe.getContent();
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (Recipe recipe : recipes) {
			recipeDtos.add(recipeService.mapToDto(recipe));
		}
        Map<String, Object> response = new HashMap<>();
        response.put("recipes", recipeDtos);
        response.put("currentPage", pageRecipe.getSize());
        response.put("totalItems", pageRecipe.getTotalElements());
        response.put("totalPages", pageRecipe.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto savedRecipeDto = recipeService.mapToDto(recipeService.saveRecipe(recipeService.toEntity(recipeDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipeDto);
    }
    
    @PostMapping("/multiple")
    public ResponseEntity<RecipeDto> createMultipleRecipe(@RequestBody List<RecipeDto> recipeDtoList) {
    	for (RecipeDto recipeDto : recipeDtoList) {
    		recipeService.saveRecipe(recipeService.toEntity(recipeDto));
		}
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable("id") int id, @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipeDto = recipeService.mapToDto(recipeService.updateRecipe(id, recipeDto));
        return ResponseEntity.ok(updatedRecipeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") int id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
}
