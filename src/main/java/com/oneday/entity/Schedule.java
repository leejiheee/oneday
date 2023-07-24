package com.oneday.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oneday.constant.ClassEnd;

import groovy.transform.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "schedule")
@Setter
@Getter
@ToString
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<Date> dates;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private List<Time> times;
    
	@Column(nullable = false)
	private int maxUser;
	
	@Column(nullable = false)
	private int nowUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oneday_class_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private OnedayClass onedayClass;
	
}
