package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Division;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
	public Division findById(int id);
}
