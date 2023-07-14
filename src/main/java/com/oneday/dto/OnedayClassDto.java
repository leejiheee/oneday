package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.OnedayClass;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnedayClassDto {
	private Long id;
	
	private String classNm;
	
	private String classDetail;
	
	private String imgUrl;
	
	private Integer price;
	
	private String teacherNm;
	
	private String region;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	@QueryProjection
	public OnedayClassDto(Long id, String classNm, String classDetail, String imgUrl, Integer price, String teacherNm, String region) {
		System.out.println("여기는 왔나ㅣ?? ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
		this.id = id;
		this.classNm = classNm;
		this.classDetail = classDetail;
		this.imgUrl = imgUrl;
		this.price = price;
		this.teacherNm = teacherNm;
		this.region = region;
	}
	

}
