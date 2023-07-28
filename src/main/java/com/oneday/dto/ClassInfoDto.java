package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoDto {
	private String date;
	
	private String time1;
	private String time2;
	private String time3;
	
	private int maxUser;
	
	private int nowUser;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//entity -> dto로 변환
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}
}
