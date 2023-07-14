package com.oneday.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassSearchDto {
	private String searchBy;
	private String searchQuery = "";
}
