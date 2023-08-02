package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassTimeDto {
	private String time;
	
	private int maxUser;
	
	private int nowUser;
	
	private static ModelMapper modelMapper = new ModelMapper();

	public static ClassTimeDto of(ClassTime classTime) {
		return modelMapper.map(classTime, ClassTimeDto.class);
	}

}
