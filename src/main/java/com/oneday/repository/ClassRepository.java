package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneday.entity.OnedayClass;

public interface ClassRepository extends JpaRepository<OnedayClass, Long>, ClassRepositoryCustom{
	List<OnedayClass> findByClassNm(String classNm);
	
} 
