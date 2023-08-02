package com.oneday.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="review")
@Setter
@Getter
@ToString
public class Review extends BaseEntity{
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int reviewScore;
	
	@Lob
	@Column(nullable = false)
	private String reviewContent;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private OnedayClass onedayClass;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
}
