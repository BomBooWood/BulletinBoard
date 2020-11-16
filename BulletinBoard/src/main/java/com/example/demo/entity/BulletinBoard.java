package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BulletinBoard")
@Getter
@Setter
public class BulletinBoard {

	/* ID */
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/* 作成日 */
	@Column(nullable = false)
	private String createDate;

	/* タイトル */
	@Column(nullable = false)
	@NotEmpty
	private String title;

	/* 内容 */
	@Column(nullable = false)
	@NotEmpty
	private String content;

	/* 作成者（多対一） */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User createUser;

	/* 多対一 */
	@ManyToOne
	@JoinColumn(name = "division_id", nullable = false)
	private Division division;

}
