package com.oneday.service;

import org.springframework.stereotype.Service;

import com.oneday.entity.Category;
import com.oneday.repository.CategoryRepository;
import com.oneday.repository.ClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public void saveClassCt(Category category) throws Exception{
		String categoryNm = "";
		
		category.updateCategory(categoryNm);
		categoryRepository.save(category);
	}
}
