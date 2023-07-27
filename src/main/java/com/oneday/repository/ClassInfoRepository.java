package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneday.entity.ClassInfo;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>{
	List<ClassInfo> findByOnedayClassIdOrderByIdAsc(Long classId); 
	
}
