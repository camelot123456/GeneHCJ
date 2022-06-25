package com.genehcj.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genehcj.config.Constants;
import com.genehcj.entity.Authority;
import com.genehcj.entity.User;
import com.genehcj.entity.enumeration.AuthProvider;
import com.genehcj.repository.AuthorityRepository;
import com.genehcj.repository.UserRepository;
import com.genehcj.security.ConstantAuthority;
import com.genehcj.service.UserService;
import com.genehcj.service.dto.UserDTO;
import com.genehcj.service.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;

	@Override
	public Page<UserDTO> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findAll(pageable).map(userMapper::toDto);
	}

	@Override
	public Optional<UserDTO> findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).map(userMapper::toDto);
	}

	@Override
	public Optional<UserDTO> save(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userMapper.toEntity(userDTO);
		user.setImageUrl(userDTO.getImageUrl() == null ? Constants.DEFAULT_IMAGE_URL : userDTO.getImageUrl());
		user.setLangKey(userDTO.getLangKey() == null ? Constants.DEFAULT_LANG_KEY : userDTO.getLangKey());
		user.setPassword(RandomString.make(60));
		user.setActivated(false);
		user.setActivationKey(RandomString.make(20));
		user.setAuthProvider(AuthProvider.LOCAL);
		if (userDTO.getAuthorities() == null) {
			Set<Authority> authorities = new HashSet<Authority>();
			authorities.add(authorityRepository.findById(ConstantAuthority.USER).get());
			user.setAuthorities(authorities);
		} else {
			Set<Authority> authorities = userDTO.getAuthorities()
					.stream()
					.map(name -> authorityRepository.findById(name).get())
					.collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		return Optional.of(userMapper.toDto(userRepository.save(user)));
	}

	@Override
	public Optional<UserDTO> update(UserDTO userDTO) {
		// TODO Auto-generated method stub
		if (userRepository.existsByLogin(userDTO.getLogin())) {
			User user = userRepository.findByLogin(userDTO.getLogin()).get();
			user.setActivated(userDTO.getActivated());
			user.setAddress(userDTO.getAddress());
			user.setBirthday(userDTO.getBirthday());
			user.setEmail(userDTO.getEmail());
			user.setFirstName(userDTO.getFirstName());
			user.setGender(userDTO.getGender());
			user.setImageUrl(userDTO.getImageUrl() == null ? Constants.DEFAULT_IMAGE_URL : userDTO.getImageUrl());
			user.setLangKey(userDTO.getLangKey() == null ? Constants.DEFAULT_LANG_KEY : userDTO.getLangKey());
			user.setLastName(userDTO.getLastName());
			user.setPhoneNumber(userDTO.getPhoneNumber());
			if (userDTO.getAuthorities() == null) {
				Set<Authority> authorities = new HashSet<Authority>();
				authorities.add(authorityRepository.findById(ConstantAuthority.USER).get());
				user.setAuthorities(authorities);
			} else {
				Set<Authority> authorities = userDTO.getAuthorities()
						.stream()
						.map(name -> authorityRepository.findById(name).get())
						.collect(Collectors.toSet());
				user.setAuthorities(authorities);
			}
			return Optional.of(userMapper.toDto(userRepository.save(user)));
		}
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public void deleteByLogin(String login) {
		// TODO Auto-generated method stub
		userRepository.deleteByLogin(login);
	}

	@Override
	public Optional<UserDTO> findByLogin(String login) {
		// TODO Auto-generated method stub
		return userRepository.findByLogin(login).map(userMapper::toDto);
	}

}
