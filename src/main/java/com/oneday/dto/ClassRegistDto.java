package com.oneday.dto;

import com.oneday.entity.RegistClass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassRegistDto {
	
	private String classNm;
	
	private int count;
	
	private int registPrice;
	
	private String imgUrl;
	
	
	public ClassRegistDto(RegistClass classRegist, String imgUrl) {
		this.classNm = classRegist.getOnedayClass().getClassNm();
		this.count = classRegist.getCount();
		this.registPrice = classRegist.getRegistPrice();
		this.imgUrl = imgUrl;
	}
}
