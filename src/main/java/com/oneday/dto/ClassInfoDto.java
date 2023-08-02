package com.oneday.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassInfo;
import com.oneday.entity.ClassTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoDto {
	private String date;
	
	private List<ClassTimeDto> classTimes = new ArrayList<>();


	
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//entity -> dto로 변환
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}
}
