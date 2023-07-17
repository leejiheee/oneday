package com.oneday.repository;

import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;

import com.oneday.dto.QMainClassDto;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.QClassImg;
import com.oneday.entity.QOnedayClass;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ClassRepositoryCustomImpl implements ClassRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public ClassRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression classNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QOnedayClass.onedayClass.classNm.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainClassDto> getMainClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		QOnedayClass onedayClass = QOnedayClass.onedayClass;
		QClassImg classImg = QClassImg.classImg;
		
		List<MainClassDto> content = queryFactory.select(
				new QMainClassDto(onedayClass.id, onedayClass.classNm, onedayClass.classDetail, classImg.imgUrl,
						onedayClass.price, onedayClass.teacherNm, onedayClass.region)
				)
				.from(classImg)
				.join(classImg.onedayClass, onedayClass)
				.where(classImg.repImgYn.eq("Y"))
				.where(classNmLike(classSearchDto.getSearchQuery()))
				.orderBy(onedayClass.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory .select(Wildcard.count) .from(classImg)
				.join(classImg.onedayClass, onedayClass) .where(classImg.repImgYn.eq("Y"))
				.where(classNmLike(classSearchDto.getSearchQuery())) .fetchOne();
	
		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<OnedayClass> getAdminOnedayClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		
		return null;
	}



}

