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
@Table(name = "time")
@Setter
@Getter
@ToString
public class Time {
	
	@Id
	@Column(name = "time_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String date;
	
	@JoinColumn(name = "info_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ClassInfo classInfo;
}
