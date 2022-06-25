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
public class PasswordResetVM {

	private String resetKey;
	
	private String resetDate;
	
	@NotNull
	@NotBlank
	@Size(min = 4, max = 128)
	private String newPassword;
	
}
