package com.oneday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneday.entity.ClassInfo;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long>{
	
}
