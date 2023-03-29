package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.List;

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
        return sectionRepository.findById(id).orElseThrow(() -> new CustomException("Section with id " + id + " not found"));
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public void deleteSection(int id) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new CustomException("Section with id " + id + " not found"));
        sectionRepository.delete(section);
    }
    
    public SectionDto mapToDto(Section section) {
        SectionDto sectionDto = new SectionDto();
        sectionDto.setName(section.getName());
        sectionDto.setContents(section.getContents());
        return sectionDto;
    }


}
