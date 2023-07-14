package com.oneday.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.OnedayClassDto;
import com.oneday.entity.OnedayClass;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ClassRepositoryCustomImpl implements ClassRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public ClassRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<OnedayClass> getAdminOnedayClassPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OnedayClassDto> getOnedayClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
			
		return null;
	}



	
}
