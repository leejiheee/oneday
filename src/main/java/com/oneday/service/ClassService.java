package com.oneday.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oneday.dto.ClassCategoryDto;
import com.oneday.dto.ClassFormDto;
import com.oneday.dto.ClassImgDto;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.ClassImg;
import com.oneday.entity.OnedayClass;
import com.oneday.repository.CategoryRepository;
import com.oneday.repository.ClassImgRepository;
import com.oneday.repository.ClassRepository;
import com.oneday.service.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

	private final ClassRepository classRepository;
	private final ClassImgService classImgService;
	private final ClassImgRepository classImgRepository;
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;
	
	public Long saveClass(ClassFormDto classFormDto, List<MultipartFile> classImgFileList) throws Exception {
		OnedayClass onedayClass = classFormDto.createClass();
		classRepository.save(onedayClass);
		Category category = new Category();
		category.setOnedayClass(onedayClass);
		
		for(int i=0; i<classImgFileList.size(); i++) {
			ClassImg classImg = new ClassImg();
			classImg.setOnedayClass(onedayClass);
			
			if(i == 0) {
				classImg.setRepImgYn("Y");	
			} else {
				classImg.setRepImgYn("N");
			}
			classImgService.saveClassImg(classImg, classImgFileList.get(i));
		}
		categoryService.saveClassCt(category);
		
		return onedayClass.getId();
	}
	
	
	@Transactional(readOnly = true)
	public ClassFormDto getClassDtl(Long classId) {
		//이미지 가져오기
		List<ClassImg> classImgList = classImgRepository.findByOnedayClassIdOrderByIdAsc(classId);
		
		List<Category> categoryList = categoryRepository.findByOnedayClassIdOrderByIdAsc(classId);
		
		//엔티티객체 -> Dto로 변환
		List<ClassImgDto> classImgDtoList = new ArrayList<>();
		
		List<ClassCategoryDto> categoryDtoList = new ArrayList<>();
		
		for(ClassImg classImg : classImgList) {
			ClassImgDto classImgDto = ClassImgDto.of(classImg);
			classImgDtoList.add(classImgDto);
		}
		
		for(Category category : categoryList) {
			ClassCategoryDto categoryDto = ClassCategoryDto.of(category);
			categoryDtoList.add(categoryDto);
		}
		
		//테이블 데이터 가져오기
		OnedayClass onedayClass = classRepository.findById(classId)
												.orElseThrow(EntityNotFoundException::new);
		
		//엔티티 -> Dto로 변환
		ClassFormDto classFormDto = ClassFormDto.of(onedayClass);
		
		//이미지 정보를 classFormDto에 넣어준다
		classFormDto.setClassImgDtoList(classImgDtoList);
		classFormDto.setClassCategoryList(categoryList);
		
		return classFormDto;
	}
	
	

	@Transactional(readOnly = true)
	public Page<MainClassDto> getMainClassDto(ClassSearchDto classSearchDto, Pageable pageable) {

		Page<MainClassDto> mainClassPage = classRepository.getMainClassPage(classSearchDto, pageable);
		return mainClassPage;
	}

}
