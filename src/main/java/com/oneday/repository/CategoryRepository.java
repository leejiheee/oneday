package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneday.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findByOnedayClassIdOrderByIdAsc(Long classId);
}
