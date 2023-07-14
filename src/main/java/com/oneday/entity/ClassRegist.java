package com.oneday.entity;

import java.time.LocalDateTime;

import com.oneday.constant.RegistStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="class_regist")
@Setter
@Getter
@ToString
public class ClassRegist{
	@Id
	@Column(name="regist_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDateTime registDate;
	
	@Enumerated(EnumType.STRING)
	private RegistStatus registStatus;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
}
