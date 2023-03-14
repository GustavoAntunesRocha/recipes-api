package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        return tagRepository.save(tag);
    }

    public Tag getTagById(int id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if(tag.isPresent()) {
            return tag.get();
        }
        else {
            throw new CustomException("Tag with id " + id + " not found");
        }
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void deleteTag(int id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if(tag.isPresent()) {
            tagRepository.deleteById(id);
        }
        else {
            throw new CustomException("Tag with id " + id + " not found");
        }
    }
    
    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }


}
