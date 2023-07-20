package com.oneday.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.OnedayClass;

public interface ClassRepositoryCustom {
	Page<OnedayClass> getAdminOnedayClassPage(ClassSearchDto classSearchDto, Pageable pageable);
	
	
	Page<MainClassDto> getMainClassPage(ClassSearchDto classSearchDto, Pageable pageable);
	
	Page<MainClassDto> getCategoryClassPage(ClassSearchDto classSearchDto, Pageable pageable, Category category);
	
	
}
