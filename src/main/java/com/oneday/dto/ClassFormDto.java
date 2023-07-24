package com.oneday.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.oneday.constant.ClassEnd;
import com.oneday.entity.Category;
import com.oneday.entity.Date;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.Schedule;
import com.oneday.entity.Time;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassFormDto {
	private Long id;
	
	@NotNull(message = "클래스 이름은 필수 입력입니다.")
	private String classNm;
	
	@NotNull(message = "가격은 필수 입력입니다.")
	private int price;

	@NotNull(message = "강사명은 필수 입력입니다.")
	private String teacherNm;
	
	@NotNull(message = "지역은 필수 입력입니다.")
	private String region;
	
	private String regionDtl;
	
	private String classDetail;
	
	private ClassEnd classEnd;
	
	@NotNull(message = "카테고리 선택은 필수입니다.")
	private Long categoryId;
	
	private Category category;
	
	@NotNull(message = "스케줄을 입력해주세요.")
	private Long scheduleId;
	
	private Schedule schedules;
	
    private String[] dates; // 여러 개의 날짜를 입력받기 위한 필드 (날짜를 문자열로 입력받습니다)
    private String[] times; // 여러 개의 시간을 입력받기 위한 필드 (시간을 문자열로 입력받습니다)
	
	
	//이미지 정보 저장
	private List<ClassImgDto> classImgDtoList = new ArrayList<>();
	
	//이미지 아이디 저장 - > 수정시 이미지 아이디들 담아둘 용도
	private List<Long> classImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
    public void printDatesAndTimes() {
        System.out.println("Dates: " + this.dates[0]);
        System.out.println("Times: " + this.times[0]);
    }
	
	//dto -> entity로 바꿈
	public OnedayClass createClass(Category category, Schedule schedules) {
		this.category = category;
		this.schedules = schedules;
		
		return modelMapper.map(this, OnedayClass.class);
	}
	
	//entity -> dto 로 바꿈
	public static ClassFormDto of(OnedayClass onedayClass) {
		return modelMapper.map(onedayClass, ClassFormDto.class);
	}

	

}