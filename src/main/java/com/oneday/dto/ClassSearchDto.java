package com.oneday.dto;

import com.oneday.constant.ClassEnd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassSearchDto {
	private String searchDateType;
	private ClassEnd searchClassend;
	private String searchBy;
	private String searchQuery = "";
}
