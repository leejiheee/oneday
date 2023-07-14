package com.oneday.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.OnedayClassDto;
import com.oneday.entity.OnedayClass;

public interface ClassRepositoryCustom {
	Page<OnedayClass> getAdminOnedayClassPage(Pageable pageable);
	
	
	Page<OnedayClassDto> getOnedayClassPage(ClassSearchDto classSearchDto, Pageable pageable);

}
