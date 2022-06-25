package com.genehcj.container.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genehcj.config.Constants;
import com.genehcj.service.UserService;
import com.genehcj.service.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/admin")
public class UserResource {

	@Value("${spring.application.name}")
	private String appName;
	
	private final UserService userService;
	
	@GetMapping("test")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok().body(appName);
	}
	
	@GetMapping("users")
	public ResponseEntity<?> getUsersList(Pageable pageable) {
		return ResponseEntity.ok().body(userService.findAll(pageable).getContent());
	}
	
	@GetMapping("users/{login}")
	public ResponseEntity<?> getUserByLogin(@PathVariable("login") String login) {
		return ResponseEntity.ok().body(userService.findByLogin(login).get());
	}
	
	@PostMapping("users")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
		Optional<UserDTO> userResponse = userService.save(userDTO);
		return ResponseEntity
				.created(new URI("/api/admin/users/" + userResponse.get().getLogin()))
				.body(userResponse.get());
	}
	
	@PutMapping("users")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok().body(userService.update(userDTO));
	}
	
	@DeleteMapping("users/{login}")
	public ResponseEntity<?> deleteUser(@PathVariable("login") @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
		userService.deleteByLogin(login);
		return ResponseEntity.noContent().build();
	}
	
}
