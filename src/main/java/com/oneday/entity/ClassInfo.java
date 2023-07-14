package com.oneday.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.oneday.constant.ClassEnd;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="class_info")
@Setter
@Getter
@ToString
public class ClassInfo{
	@Id
	@Column(name="class_info_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique = true, nullable = false, length = 255)
	private Date date;
	
	@Column(unique = true, nullable = false, length = 255)	
	private Date time;
	
	@Column(unique = true, nullable = false, length = 255)	
	private int maxUser;
	
	@Column(unique = true, nullable = false, length = 255)	
	private int nowUser;
	
	@Enumerated(EnumType.STRING)
	private ClassEnd classEnd;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;

	
}
