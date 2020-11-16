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
@Getter
@Setter
@Table(name = "division")
public class Division {
	@Id
	@Column(name = "division_id", nullable = false)
	private int divisionId;
	@Column
	private String name;

	@OneToMany(mappedBy = "division")
	private List<BulletinBoard> bbs;

}
