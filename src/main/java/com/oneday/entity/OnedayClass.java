package com.oneday.entity;

import com.oneday.constant.ClassEnd;
import com.oneday.dto.ClassFormDto;

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
	private String teacherNm;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String classDetail;
	
	@Column(nullable = false)
	private int price;	

	@Column(nullable = false, length = 255)
	private String region;
	
	@Enumerated(EnumType.STRING)
	private ClassEnd classEnd; //마감 상태
	
	//class엔티티 수정
	public void updateClass(ClassFormDto classFormDto) {
		this.classNm = classFormDto.getClassNm();
		this.price = classFormDto.getPrice();
		this.teacherNm = classFormDto.getTeacherNm();
		this.region = classFormDto.getRegion();
		this.classDetail = classFormDto.getClassDetail();
		this.classEnd = classFormDto.getClassEnd();
	}
	
}
