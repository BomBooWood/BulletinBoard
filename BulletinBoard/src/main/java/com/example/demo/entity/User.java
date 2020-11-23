package com.example.demo.entity;

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

	@Column(length = 64)
	private String encodedPassword;

	@Column
	private String uuid;

	@Column
	private String mailAddress;

	@Column
	private boolean activate;

	@OneToMany(mappedBy = "division")
	private List<BulletinBoard> bbs;

}
