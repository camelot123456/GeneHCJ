package com.genehcj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genehcj.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
