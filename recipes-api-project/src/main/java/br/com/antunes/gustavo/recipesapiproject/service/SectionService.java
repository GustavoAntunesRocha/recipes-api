package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.antunes.gustavo.recipesapiproject.dto.SectionDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Section;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.repository.SectionRepository;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public Section getSectionById(int id) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            return section.get();
        }
        else {
            throw new CustomException("Section with id " + id + " not found");
        }
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public void deleteSection(int id) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            sectionRepository.deleteById(id);
        }
        else {
            throw new CustomException("Section with id " + id + " not found");
        }
    }
    
    public SectionDto mapToDto(Section section) {
        SectionDto sectionDto = new SectionDto();
        sectionDto.setName(section.getName());
        sectionDto.setContents(section.getContents());
        return sectionDto;
    }


}
