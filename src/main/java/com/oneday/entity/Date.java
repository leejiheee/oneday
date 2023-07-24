package com.oneday.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import groovy.transform.ToString;
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

@Entity
@Table(name = "date")
@Setter
@Getter
@ToString
public class Date {
    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(nullable = false, length = 100)
    private String date;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	@OnDelete(action= OnDeleteAction.CASCADE)
    private Schedule schedule;
	
    public void setDate(String date) {
        this.date = date;
    }
}
