package com.oneday.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistDto {
	
	@NotNull(message = "클래스 아이디는 필수 입력입니다.")
	private Long classId;
	
	private String date;
	
	private String time;

	@Min(value = 1, message = "최소 신청 인원은 1명 입니다.")
	@Max(value = 10, message = "최대 신청 인원은 10명 입니다.")
	private int count;
}
