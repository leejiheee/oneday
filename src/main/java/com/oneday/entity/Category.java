package com.oneday.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="category")
@Setter
@Getter
@ToString
public class Category{
	@Id
	@Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String categoryNm;
	
	
	@OneToOne
	@JoinColumn(name="class_id")
	private OnedayClass onedayClass;
	
	
	public void updateCategory(String categoryNm) {
		this.categoryNm = categoryNm;
	}
}
