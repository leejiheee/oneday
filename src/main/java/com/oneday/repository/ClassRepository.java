package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oneday.dto.MainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.ClassInfo;
import com.oneday.entity.OnedayClass;

public interface ClassRepository extends JpaRepository<OnedayClass, Long>, ClassRepositoryCustom{
	List<OnedayClass> findByClassNm(String classNm);

} 
