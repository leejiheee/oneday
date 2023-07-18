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
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String categoryNm;
	
	
}
