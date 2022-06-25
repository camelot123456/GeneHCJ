package com.genehcj.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.genehcj.service.dto.UserDTO;

public interface UserService {

	Page<UserDTO> findAll(Pageable pageable);
	
	Optional<UserDTO> findById(Long id);
	
	Optional<UserDTO> findByLogin(String login);
	
	Optional<UserDTO> save(UserDTO userDTO);
	
	Optional<UserDTO> update(UserDTO userDTO);
	
	void deleteById(Long id);
	
	void deleteByLogin(String login);
	
}
