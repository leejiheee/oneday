package com.oneday.entity;

import com.oneday.dto.RegistDto;

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
@Table(name = "regist_class")
@Setter
@Getter
@ToString
public class RegistClass extends BaseEntity{
	
	@Id
	@Column(name = "regist_class_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private OnedayClass onedayClass;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regist_id")
	private Regist regist;
	
	private int registPrice; //신청 가격
	
	private int count; //신청인원
	
	private String date;
	
	private String time;
	
	
	public static RegistClass createClassRegist(OnedayClass onedayClass, ClassTime classTime,RegistDto registDto) {
		RegistClass classRegist = new RegistClass();
		classRegist.setOnedayClass(onedayClass);
		classRegist.setCount(registDto.getCount());
		classRegist.setDate(registDto.getDate());
		classRegist.setTime(registDto.getTime());
		classRegist.setRegistPrice(registDto.getTotalPrice());
		
		
		classTime.removeUser(registDto.getCount()); //현재 신청인원을 증가시킴
		
		return classRegist;
	}
	
	public int getTotalPrice() {
		return registPrice * count;
	}
	

	
	
}
