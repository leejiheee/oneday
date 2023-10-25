package com.oneday.dto;

import org.modelmapper.ModelMapper;

import com.oneday.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
	private int reviewScore;
	
	private String reviewContent;
	
	private Long classId;
	
	private Long registId;
	
	private static ModelMapper modelMapper = new ModelMapper();

	
	public static ReviewDto of(Review review) {
		return modelMapper.map(review, ReviewDto.class);
	}
}
