package com.oneday.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oneday.constant.ClassEnd;
import com.oneday.dto.ClassFormDto;
import com.oneday.exception.OutOfUserException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.ToString;

@Entity
@Table(name="class_info")
@Setter
@Getter
@ToString
public class ClassInfo{
	
	@Id
	@Column(name = "info_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String date;


	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "class_id")
	private OnedayClass onedayClass;
	
    @OneToMany(mappedBy = "classInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassTime> classTimes = new ArrayList<>();

	
	
}
