package com.oneday.dto;

import com.oneday.entity.Category;
import com.oneday.entity.ClassInfo;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainClassDto {
	private Long id;
	
	private String classNm;
	
	private String classDetail;
	
	private String imgUrl;
	
	private Integer price;
	
	private String teacherNm;
	
	private String region;
	
	private String regionDtl;
	
	private Category category;
	
	private ClassInfo classInfo;
	
	@QueryProjection
	public MainClassDto(Long id, String classNm, String classDetail, String imgUrl, Integer price, String teacherNm, String region, String regionDtl, Category category) {
		this.id = id;
		this.classDetail = classDetail;
		this.classNm = classNm;
		this.imgUrl = imgUrl;
		this.price = price;
		this.teacherNm = teacherNm;
		this.region = region;
		this.regionDtl = regionDtl;
		this.category = category;
	}
}
