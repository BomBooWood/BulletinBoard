package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BulletinBoard;

public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Integer> {
	BulletinBoard findById(int id);

	void deleteById(int id);

}
