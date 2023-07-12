package com.oneday.entity;

import java.time.LocalDateTime;

import com.oneday.constant.ClassEnd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="class_info")
@Setter
@Getter
@ToString
public class ClassInfo {
	@Id
	@Column(name="class_info_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private LocalDateTime classDate;
	
	@Column(nullable = false)
	private LocalDateTime classTime;
	
	@Column(nullable = false)
	private int maxUser;
	
	@Enumerated(EnumType.STRING)
	private ClassEnd classEnd;

	@Column(nullable = false)
	private int nowUser;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
	
}
