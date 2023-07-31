package com.oneday.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.oneday.constant.RegistStatus;
import com.oneday.entity.Regist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistHistDto {
	private Long registId;
	
	private String registDate;
	
	private RegistStatus registStatus;
	
	private List<ClassRegistDto> classRegistDtoList = new ArrayList<>();
	
	public RegistHistDto(Regist regist) {
		this.registId = regist.getId();
		this.registDate = regist.getRegistDate()
							.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
		this.registStatus = regist.getRegistStatus();
	}
	
	public void addClassRegistDto(ClassRegistDto classRegistDto) {
		this.classRegistDtoList.add(classRegistDto);
	}
}
