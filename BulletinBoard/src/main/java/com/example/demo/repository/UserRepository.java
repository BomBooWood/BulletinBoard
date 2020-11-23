package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);

	User findByUuid(String uuid);

	boolean existsByUsername(String username);

	boolean existsByUuid(String uuid);
}
