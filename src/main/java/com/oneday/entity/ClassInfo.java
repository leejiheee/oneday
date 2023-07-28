package com.oneday.entity;

import com.oneday.constant.ClassEnd;
import com.oneday.dto.ClassFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="class_info")
@Setter
@Getter
@ToString
public class ClassInfo {
	
	@Id
	@Column(name = "info_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String date;
	
	@Column(nullable = false, length = 100)
	private String time1;

	private String time2;
	
	private String time3;
	@Column(nullable = false)
	private int maxUser;
	
	@Column(nullable = false)
	private int nowUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private OnedayClass onedayClass;
	
	/*
	public void updateInfo(ClassFormDto classFormDto) {
		this.date = classFormDto.getDate();
		this.time = classFormDto.getTime();
		this.maxUser = classFormDto.getMaxUser();
		this.nowUser = classFormDto.getNowUser();
	}
	*/
	
	
}
