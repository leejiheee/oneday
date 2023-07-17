package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassCategoryDto {
	private Long id;
	
	private String categoryName;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ClassCategoryDto of(Category category) {
		return modelMapper.map(category, ClassCategoryDto.class);
	}
}
