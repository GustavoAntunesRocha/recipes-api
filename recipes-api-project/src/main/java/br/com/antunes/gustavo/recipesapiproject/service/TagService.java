package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.recipesapiproject.dto.TagDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Tag;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
    	if(searchTagByName(tag.getName()) == null){
    		return tagRepository.save(tag);    		
    	}
    	throw new CustomException("Tag with the name: " + tag.getName() + " already exists!");
    }

    public Tag getTagById(int id) {
        return tagRepository.findById(id).orElseThrow(() -> new CustomException("Tag with id " + id + " not found"));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public void deleteTag(int id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new CustomException("Tag with id " + id + " not found"));
        if(!tag.getRecipes().isEmpty()) {
        	throw new CustomException("Tag with id " + id + " it's in use!");
        }
        tagRepository.deleteById(id);
    }
    
    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }
    
    public List<Tag> mapTagNamesToEntities(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            Tag tag = searchTagByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                saveTag(tag);
            }
            tags.add(tag);
        }
        return tags;
    }

	public Tag searchTagByName(String tagName) {
		return tagRepository.findByName(tagName);
	}
	
	public Page<Tag> searchTagByName(String name, Pageable pageable){
		return tagRepository.findByName(name, pageable);
	}


}
