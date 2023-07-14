package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity -> dto로 변환
	public static ClassImgDto of(ClassImg classImg) {
		return modelMapper.map(classImg, ClassImgDto.class);
	}
	
}
