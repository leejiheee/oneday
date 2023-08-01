package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneday.entity.ClassTime;


public interface ClassTimeRepository extends JpaRepository<ClassTime, Long>{
	List<ClassTime> findByClassInfoIdOrderByIdAsc(Long infoId);
}
