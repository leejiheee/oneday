package com.oneday.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.oneday.entity.ClassInfo;
import com.oneday.entity.OnedayClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfoDto {
	private int id;
	
	private Date date;
	
	private Date time;
	
	private int maxUser;
	
	private int nowUser;
	
	private String classEnd;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ClassInfoDto of(ClassInfo classInfo) {
		return modelMapper.map(classInfo, ClassInfoDto.class);
	}


}

