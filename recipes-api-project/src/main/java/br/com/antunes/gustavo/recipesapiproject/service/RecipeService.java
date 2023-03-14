package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.recipesapiproject.dto.RecipeDto;
import br.com.antunes.gustavo.recipesapiproject.dto.SectionDto;
import br.com.antunes.gustavo.recipesapiproject.dto.TagDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Recipe;
import br.com.antunes.gustavo.recipesapiproject.entity.Section;
import br.com.antunes.gustavo.recipesapiproject.entity.Tag;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.repository.RecipeRepository;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            return recipe.get();
        }
        else {
            throw new CustomException("Recipe with id " + id + " not found");
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public void deleteRecipe(int id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
        }
        else {
            throw new CustomException("Recipe with id " + id + " not found");
        }
    }
    
    public RecipeDto mapToDto(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        List<SectionDto> sectionDtos = new ArrayList<>();
        for (Section section : recipe.getSections()) {
            SectionDto sectionDto = new SectionDto();
            sectionDto.setName(section.getName());
            sectionDto.setContents(section.getContents());
            sectionDtos.add(sectionDto);
        }
        recipeDto.setSections(sectionDtos);
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : recipe.getTags()) {
            TagDto tagDto = new TagDto();
            tagDto.setId(tag.getId());
            tagDto.setName(tag.getName());
            tagDtos.add(tagDto);
        }
        recipeDto.setTags(tagDtos);
        return recipeDto;
    }


}

