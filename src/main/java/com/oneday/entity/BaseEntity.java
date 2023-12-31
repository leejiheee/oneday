package com.oneday.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@EntityListeners(value= {AuditingEntityListener.class})
@MappedSuperclass
@Setter
@Getter
public abstract class BaseEntity extends BaseTimeEntity{
	
	@CreatedBy
	@Column(updatable = false)
	private String createBy; //등록자
	
	@LastModifiedBy
	private String modifiedBy; //수정자
}
