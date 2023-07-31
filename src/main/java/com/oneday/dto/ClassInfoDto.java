package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassInfo;
import com.oneday.entity.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoDto {
	private String date;
	
	private Time time;

	private int maxUser;
	
	private int nowUser;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//entity -> dto로 변환
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}
}
