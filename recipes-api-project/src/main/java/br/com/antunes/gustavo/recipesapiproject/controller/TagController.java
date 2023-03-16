package br.com.antunes.gustavo.recipesapiproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.recipesapiproject.dto.TagDto;
import br.com.antunes.gustavo.recipesapiproject.entity.Tag;
import br.com.antunes.gustavo.recipesapiproject.exception.ApiErrorResponse;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.service.TagService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
	
	private final TagService tagService;
	
	@PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) {
        try {
            Tag tag = tagService.saveTag(tagDtoToEntity(tagDto));
            return new ResponseEntity<>(entityToTagDto(tag), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(handleCustomException(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable int id) {
        try {
            Tag tag = tagService.getTagById(id);
            return new ResponseEntity<>(entityToTagDto(tag), HttpStatus.OK);
        } catch (CustomException e) {
        	return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagDto> tagDtos = tags.stream()
                .map(this::entityToTagDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable int id) {
        try {
            tagService.deleteTag(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
        	return new ResponseEntity<>(handleCustomException(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    private TagDto entityToTagDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }

    private Tag tagDtoToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        return tag;
    }
    
	public ApiErrorResponse handleCustomException(CustomException e, HttpStatus status) {
		LocalDateTime timestamp = LocalDateTime.now();
		ApiErrorResponse errorResponse = new ApiErrorResponse(status.toString(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(timestamp), e.getMessage());
		return errorResponse;
	}

}
