package br.com.antunes.gustavo.recipesapiproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.antunes.gustavo.recipesapiproject.dto.UserDTO;
import br.com.antunes.gustavo.recipesapiproject.entity.UserEntity;
import br.com.antunes.gustavo.recipesapiproject.exception.CustomException;
import br.com.antunes.gustavo.recipesapiproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTO createUser(UserEntity user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO getUserById(int id) throws CustomException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserDTO.class);
        } else {
            throw new CustomException("User not found with ID " + id);
        }
    }

    public UserDTO getUserByEmail(String email) throws CustomException {
    	UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("User not found with email " + email);
        }
        return modelMapper.map(user, UserDTO.class);
    }
    
    public UserEntity findUserByEmail(String email) throws CustomException {
    	UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("User not found with email " + email);
        }
        return user;
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(UserEntity user) throws CustomException {
        Optional<UserEntity> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
        	UserEntity savedUser = userRepository.save(user);
            return modelMapper.map(savedUser, UserDTO.class);
        } else {
            throw new CustomException("User not found with ID " + user.getId());
        }
    }

    public void deleteUserById(int id) throws CustomException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new CustomException("User not found with ID " + id);
        }
    }

    public String getUserDTOAsJson(int id) throws CustomException, JsonProcessingException {
        UserDTO userDTO = getUserById(id);
        return objectMapper.writeValueAsString(userDTO);
    }

    public String getAllUsersAsJson() throws JsonProcessingException {
        List<UserDTO> userDTOs = getAllUsers();
        return objectMapper.writeValueAsString(userDTOs);
    }
}
