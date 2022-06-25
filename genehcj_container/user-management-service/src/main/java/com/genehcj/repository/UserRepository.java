package com.genehcj.repository;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genehcj.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	void deleteByLogin(String login);

	Optional<User> findByLogin(@NotNull String login);

	boolean existsByLogin(@NotNull String login);
	
}
