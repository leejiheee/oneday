package com.oneday.entity;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	private OnedayClass onedayClass;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regist_id")
	private Regist regist;
	
	private int registPrice; //신청 가격
	
	private int count; //신청인원
	
	
	public static RegistClass createClassRegist(OnedayClass onedayClass, ClassInfo classInfo, int count) {
		RegistClass classRegist = new RegistClass();
		classRegist.setOnedayClass(onedayClass);
		classRegist.setCount(count);
		classRegist.setRegistPrice(onedayClass.getPrice());
		
		classInfo.removeUser(count); //현재 신청인원을 증가시킴
		
		return classRegist;
	}
	
	public int getTotalPrice() {
		return registPrice * count;
	}
	
}
