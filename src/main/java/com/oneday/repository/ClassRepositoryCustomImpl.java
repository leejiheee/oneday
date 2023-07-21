package com.oneday.repository;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.oneday.constant.ClassEnd;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;

import com.oneday.dto.QMainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.QCategory;
import com.oneday.entity.QClassImg;
import com.oneday.entity.QOnedayClass;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.QItem;

import jakarta.persistence.EntityManager;

public class ClassRepositoryCustomImpl implements ClassRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public ClassRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null;
		else if(StringUtils.equals("1d", searchDateType))
				dateTime = dateTime.minusDays(1); //현재 날짜로부터 1일 전
		else if(StringUtils.equals("1w", searchDateType))
			dateTime = dateTime.minusMonths(1); //현재날짜로부터 1주 전
		else if(StringUtils.equals("6m", searchDateType))
			dateTime = dateTime.minusMonths(6); // 현재 날짜로부터 6달 전

	
		return QItem.item.regTime.after(dateTime);
	}
	
	//상태를 전체로 했을 때 null이 들어있으므로 처리를 한번 해준다.
	private BooleanExpression searchClassEndEq(ClassEnd searchClassEnd) {
		return searchClassEnd == null ? null : QOnedayClass.onedayClass.classEnd.eq(searchClassEnd);
	}
	
	private BooleanExpression classNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QOnedayClass.onedayClass.classNm.like("%" + searchQuery + "%");
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			//등록자로 검색시
			return QItem.item.itemNm.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createBy", searchBy)) {
			return QItem.item.createBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<MainClassDto> getMainClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		QOnedayClass onedayClass = QOnedayClass.onedayClass;
		QClassImg classImg = QClassImg.classImg;
		
		List<MainClassDto> content = queryFactory.select(
				new QMainClassDto(onedayClass.id, onedayClass.classNm, onedayClass.classDetail, classImg.imgUrl,
						onedayClass.price, onedayClass.teacherNm, onedayClass.region, onedayClass.regionDtl, onedayClass.category)
				)
				.from(classImg)
				.join(classImg.onedayClass, onedayClass)
				.where(classImg.repimgYn.eq("Y"))
				.where(classNmLike(classSearchDto.getSearchQuery()))
				.orderBy(onedayClass.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory .select(Wildcard.count) .from(classImg)
				.join(classImg.onedayClass, onedayClass) .where(classImg.repimgYn.eq("Y"))
				.where(classNmLike(classSearchDto.getSearchQuery())) .fetchOne();
	
		return new PageImpl<>(content, pageable, total);
	}
	
	@Override
	public Page<MainClassDto> getCategoryClassPage(ClassSearchDto classSearchDto, Pageable pageable, Category category) {
		QOnedayClass onedayClass = QOnedayClass.onedayClass;
		QClassImg classImg = QClassImg.classImg;
		
		QCategory category1 = QCategory.category;
		
		  List<MainClassDto> content = queryFactory.select(
				  new QMainClassDto(onedayClass.id, onedayClass.classNm, onedayClass.classDetail, classImg.imgUrl, onedayClass.price, onedayClass.teacherNm,
		  onedayClass.region, onedayClass.regionDtl, onedayClass.category) )
		  .from(classImg)
		  .join(classImg.onedayClass, onedayClass)
		  .where(classImg.repimgYn.eq("Y"))
		  .join(onedayClass.category, category1)
		  .where(classNmLike(classSearchDto.getSearchQuery()))
		  .orderBy(onedayClass.id.desc())
		  .offset(pageable.getOffset())
		  .limit(pageable.getPageSize())
		  .fetch();

				
		return null;
	}

	@Override
	public Page<OnedayClass> getAdminOnedayClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		
		List<OnedayClass> content = queryFactory
				.selectFrom(QOnedayClass.onedayClass)
				.where(regDtsAfter(classSearchDto.getSearchDateType()), searchClassEndEq(classSearchDto.getSearchClassend()),
						searchByLike(classSearchDto.getSearchBy(), classSearchDto.getSearchQuery()))
				.orderBy(QOnedayClass.onedayClass.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory.select(Wildcard.count).from(QOnedayClass.onedayClass)
				.where(regDtsAfter(classSearchDto.getSearchDateType()), searchClassEndEq(classSearchDto.getSearchClassend()),
						searchByLike(classSearchDto.getSearchBy(), classSearchDto.getSearchQuery()))
				.fetchOne();

		
		return new PageImpl<>(content, pageable, total);
	}




}

