package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, String> {
	BulletinBoard findById(int id);

	void deleteById(int id);

}
