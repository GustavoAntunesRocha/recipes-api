package br.com.antunes.gustavo.recipesapiproject.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDto {
    private int id;
    private String name;
    private List<SectionDto> sections;
    private List<TagDto> tags;

}

