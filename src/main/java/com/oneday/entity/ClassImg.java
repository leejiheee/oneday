package com.oneday.entity;

import com.oneday.constant.RepImg;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="class_img")
@Setter
@Getter
@ToString
public class ClassImg {
	@Id
	@Column(name="img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String imgName;
	
	@Enumerated(EnumType.STRING)
	private RepImg repImg;
	
	@ManyToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
}
