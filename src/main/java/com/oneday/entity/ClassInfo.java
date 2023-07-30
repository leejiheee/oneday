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
public class ClassInfo{
	
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
	
	//인원수 감소시키는 메소드
	public void removeUser(int maxUser) {
		int nowUser = this.nowUser + 1;
		
		/*
		 * if(nowUser < maxUser) { throw new OutOfUserException("모집인원이 마감되었습니다."); }
		 	메세지 띄워야됨
		 */
		
		this.nowUser = nowUser;
	}
	
}
