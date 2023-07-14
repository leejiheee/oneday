package com.oneday.entity;

import com.oneday.constant.ClassEnd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="oneday_class")
@Setter
@Getter
@ToString
public class OnedayClass{
	
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String classNm;
		
	@Column(nullable = false, length = 50)
	private String teacher;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String classDetail;
	
	@Column(nullable = false)
	private int price;	

	@Column(nullable = false, length = 255)
	private String region;
	
}
