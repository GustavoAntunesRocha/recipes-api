package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.recipesapiproject.dto.RecipeDto;
import br.com.antunes.gustavo.recipesapiproject.dto.SectionDto;
import br.com.antunes.gustavo.recipesapiproject.dto.TagDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Recipe;
import br.com.antunes.gustavo.recipesapiproject.entity.Section;
import br.com.antunes.gustavo.recipesapiproject.entity.Tag;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    
    private final TagService tagService;
    
    private static final ModelMapper modelMapper = new ModelMapper();

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    
    public Recipe updateRecipe(int id, RecipeDto recipeDto) {
    	Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException("Recipe with id " + id + " not found"));
        recipe = toEntity(recipeDto); 
        return recipe;
    }

    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).orElseThrow(() -> new CustomException("Recipe with id " + id + " not found"));
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public void deleteRecipe(int id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new CustomException("Recipe with id " + id + " not found"));
        recipeRepository.delete(recipe);
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
        if(recipe.getTags() != null) {
	        for (Tag tag : recipe.getTags()) {
	            TagDto tagDto = new TagDto();
	            tagDto.setId(tag.getId());
	            tagDto.setName(tag.getName());
	            tagDtos.add(tagDto);
	        }
        }
        recipeDto.setTags(tagDtos);
        return recipeDto;
    }
    
    public Recipe toEntity(RecipeDto recipeDTO) {
        Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
        
        // Map tags
        if(recipeDTO.getTags() != null) {
        	List<String> tagsNames = new ArrayList<>();
        	for (TagDto tagDTO : recipeDTO.getTags()) {
				tagsNames.add(tagDTO.getName());
			}
        	recipe.setTags(tagService.mapTagNamesToEntities(tagsNames));
        	
        }
        
        // Map sections
        List<Section> sections = recipeDTO.getSections().stream()
                .map(sectionDTO -> modelMapper.map(sectionDTO, Section.class))
                .collect(Collectors.toList());
        recipe.setSections(sections);
        
        return recipe;
    }

	public Page<Recipe> searchRecipeByName(String name, Pageable paging) {
		return recipeRepository.findByNameContainingIgnoreCase(name, paging);
	}


}

