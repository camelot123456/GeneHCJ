package com.genehcj.service.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.genehcj.config.Constants;
import com.genehcj.entity.Authority;
import com.genehcj.entity.enumeration.AuthProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

	private Long id;
	
	@NotNull
	@Size(max = 50)
	private String firstName;
	
	@NotNull
	@Size(max = 50)
	private String lastName;
	
	@Email
	@Size(min = 4, max = 254)
	private String email;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 4, max = 64)
	private String login;
	
	@Size(max = 256)
	private String imageUrl;
	
	@Size(min = 2, max = 10)
	private String langKey;
	
	private LocalDate birthday;

	private Boolean gender;
	
	@Size(max = 256)
	private String address;
	
	@Size(min = 3, max = 20)
	private String phoneNumber;
	
	private Boolean activated;
	
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;
	
	private Set<String> authorities;
	
}
