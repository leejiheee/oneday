package com.oneday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="date_time")
@Setter
@Getter
@ToString
public class DateTime {
	@Id
	@Column(name="date_time_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private LocalDateTime classDate;
	
	@Column(nullable = false)
	private LocalDateTime classTime;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
}
