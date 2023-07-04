package com.oneday.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="category")
@Setter
@Getter
@ToString
public class Category {
	@Id
	@Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String categoryName;
	
	
	@OneToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
}
