package com.oneday.dto;

import com.oneday.entity.RegistClass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassRegistDto {
	
	private Long classId;
	
	private String classNm;
	
	private int count;
	
	private int registPrice;
	
	private String imgUrl;
	
	private String date;
	
	private String time;
	
	

	
	
	public ClassRegistDto(RegistClass classRegist, String imgUrl) {
		this.classId = classRegist.getOnedayClass().getId();
		this.classNm = classRegist.getOnedayClass().getClassNm();
		this.count = classRegist.getCount();
		this.registPrice = classRegist.getRegistPrice();
		this.imgUrl = imgUrl;
		this.date = classRegist.getDate();
		this.time = classRegist.getTime();
	}
}
