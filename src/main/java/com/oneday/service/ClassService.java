package com.oneday.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oneday.dto.ClassFormDto;
import com.oneday.dto.ClassImgDto;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.OnedayClassDto;
import com.oneday.entity.ClassImg;
import com.oneday.entity.OnedayClass;
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
	
	public Long saveClass(ClassFormDto classFormDto, List<MultipartFile> classImgFileList) throws Exception {
		OnedayClass onedayClass = classFormDto.createClass();
		classRepository.save(onedayClass);
		
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
		
		return onedayClass.getId();
	}
	
	
	@Transactional(readOnly = true)
	public ClassFormDto getClassDtl(Long classId) {
		//이미지 가져오기
		List<ClassImg> classImgList = classImgRepository.findByOnedayClassIdOrderByIdAsc(classId);
		
		//엔티티객체 -> Dto로 변환
		List<ClassImgDto> classImgDtoList = new ArrayList<>();
		
		for(ClassImg classImg : classImgList) {
			ClassImgDto classImgDto = ClassImgDto.of(classImg);
			classImgDtoList.add(classImgDto);
		}
		
		//테이블 데이터 가져오기
		OnedayClass onedayClass = classRepository.findById(classId)
												.orElseThrow(EntityNotFoundException::new);
		
		//엔티티 -> Dto로 변환
		ClassFormDto classFormDto = ClassFormDto.of(onedayClass);
		
		//이미지 정보를 classFormDto에 넣어준다
		classFormDto.setClassImgDtoList(classImgDtoList);
		
		return classFormDto;
	}
	

	@Transactional(readOnly = true)
	public Page<OnedayClassDto> getOnedayClassDtoPage(ClassSearchDto classSearchDto, Pageable pageable) {
		System.out.println("왜안돼애애애애애애애애ㅐ애애애애애애애애앵");
		Page<OnedayClassDto> onedayClassPage = classRepository.getOnedayClassPage(classSearchDto, pageable);
		return onedayClassPage;
	}

}
