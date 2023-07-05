package com.oneday.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="review")
@Setter
@Getter
@ToString
public class Review {
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int reviewScore;
	
	@Lob
	@Column(nullable = false)
	private String reviewContent;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
}