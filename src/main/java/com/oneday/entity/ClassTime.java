package com.oneday.entity;

import com.oneday.exception.OutOfUserException;

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
@Table(name = "class_time")
@Setter
@Getter
@ToString
public class ClassTime {
	@Id
	@Column(name = "class_time_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String time;
	
	@Column(nullable = false)
	private int maxUser = 10;
	
	@Column(nullable = false)
	private int nowUser = 0;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id")
	private ClassInfo classInfo;
	
	//모집가능 인원 감소
	public void removeUser(int count) {
		int user = this.nowUser + count;
		
		if(user > maxUser) {
			throw new OutOfUserException("모집이 마감되었습니다.");
		}
		
		this.nowUser = user;
	}
	
	//신청한 회원이 취소했을 때
	public void addUser(int count) {
		this.nowUser -= count;
	}
	

}
