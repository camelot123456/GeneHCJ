package com.genehcj.container.rest.vm;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.genehcj.config.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVM {

	@NotNull
	@Size(min = 3, max = 50)
	private String firstName;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String lastName;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 4, max = 64)
	private String username;
	
	@Email
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min = 4, max = 128)
	private String password;
	
}
