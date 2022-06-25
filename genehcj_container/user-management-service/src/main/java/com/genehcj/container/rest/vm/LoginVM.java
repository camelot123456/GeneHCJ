package com.genehcj.container.rest.vm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVM {

	@NotNull
	@NotBlank
	@Size(min = 4, max = 64)
	private String login;
	
	@NotNull
	@NotBlank
	@Size(min = 4, max = 128)
	private String password;
	
	private Boolean remeberMe;
	
}
