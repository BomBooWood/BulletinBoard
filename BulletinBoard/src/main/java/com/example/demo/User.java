package com.example.demo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	@Id
	@Column(name = "user_id")
	private String username;
	@Column
	private String encodedPassword;

	@OneToMany(mappedBy = "division")
	private List<BulletinBoard> bbs;

}
