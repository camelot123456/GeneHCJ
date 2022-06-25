package com.genehcj.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.genehcj.entity.User;
import com.genehcj.service.dto.UserDTO;

@Component
public class UserMapper implements EntityMapper<UserDTO, User>{

	@Override
	public User toEntity(UserDTO dto) {
		// TODO Auto-generated method stub
		return User.builder()
				.activated(dto.getActivated())
				.address(dto.getAddress())
				.authProvider(dto.getAuthProvider())
				.birthday(dto.getBirthday())
				.email(dto.getEmail())
				.firstName(dto.getFirstName())
				.gender(dto.getGender())
				.id(dto.getId())
				.imageUrl(dto.getImageUrl())
				.langKey(dto.getLangKey())
				.lastName(dto.getLastName())
				.login(dto.getLogin())
				.phoneNumber(dto.getPhoneNumber())
				.build();
	}

	@Override
	public UserDTO toDto(User entity) {
		// TODO Auto-generated method stub
		return UserDTO.builder()
				.activated(entity.getActivated())
				.address(entity.getAddress())
				.authProvider(entity.getAuthProvider())
				.birthday(entity.getBirthday())
				.email(entity.getEmail())
				.firstName(entity.getFirstName())
				.gender(entity.getGender())
				.id(entity.getId())
				.imageUrl(entity.getImageUrl())
				.langKey(entity.getLangKey())
				.lastName(entity.getLastName())
				.login(entity.getLogin())
				.phoneNumber(entity.getPhoneNumber())
				.build();
	}

	@Override
	public List<User> toEntity(List<UserDTO> dtoList) {
		// TODO Auto-generated method stub
		return dtoList.stream()
				.map(dto -> toEntity(dto))
				.toList();
	}

	@Override
	public List<UserDTO> toDto(List<User> entityList) {
		// TODO Auto-generated method stub
		return entityList.stream()
				.map(entity -> toDto(entity))
				.toList();
	}

}
