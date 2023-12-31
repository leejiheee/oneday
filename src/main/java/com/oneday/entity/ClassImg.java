package com.oneday.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oneday.constant.RepImg;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="class_img")
@Setter
@Getter
public class ClassImg{
	@Id
	@Column(name="class_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="class_id")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private OnedayClass onedayClass;
	
	public void updateClassImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
