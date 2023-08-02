package com.oneday.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.oneday.constant.RegistStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="regists")
@Setter
@Getter
@ToString
public class Regist extends BaseEntity{
	@Id
	@Column(name="regist_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime registDate;
	
	@Enumerated(EnumType.STRING)
	private RegistStatus registStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@OneToMany(mappedBy = "regist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<RegistClass> classRegists = new ArrayList<>();
	
	public void addClassRegist(RegistClass classRegist) {
		this.classRegists.add(classRegist);
		classRegist.setRegist(this);
	}
	
	
	public static Regist createRegist(Member member, List<RegistClass> classRegistList) {
		Regist regist = new Regist();
		regist.setMember(member);
		
		for(RegistClass classRegist : classRegistList) {
			regist.addClassRegist(classRegist);
		}
		
		regist.setRegistStatus(RegistStatus.REGIST);
		regist.setRegistDate(LocalDateTime.now());
		
		return regist;
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		for(RegistClass classRegist : classRegists) {
			totalPrice += classRegist.getTotalPrice();
		}
		
		return totalPrice;
	}
}
