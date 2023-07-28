package com.oneday.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.oneday.dto.ClassFormDto;
import com.oneday.dto.ClassImgDto;
import com.oneday.dto.ClassInfoDto;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.ClassImg;
import com.oneday.entity.ClassInfo;
import com.oneday.entity.Member;
import com.oneday.entity.OnedayClass;
import com.oneday.repository.CategoryRepository;
import com.oneday.repository.ClassImgRepository;
import com.oneday.repository.ClassInfoRepository;
import com.oneday.repository.ClassRepository;
import com.oneday.repository.MemberRepository;
import com.oneday.service.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

	private final ClassRepository classRepository;
	private final ClassInfoRepository classInfoRepository;
	private final ClassImgService classImgService;
	private final ClassImgRepository classImgRepository;
	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;

	
	//클래스 등록
	public Long saveClass(ClassFormDto classFormDto, List<MultipartFile> classImgFileList) throws Exception {
		
		Category category = categoryRepository.findById(classFormDto.getCategoryId())
												.orElseThrow(EntityNotFoundException::new);
		
		OnedayClass onedayClass = classFormDto.createClass(category, classFormDto.getClassInfos());
		
		int maxUser = classFormDto.getMaxUser();
		int nowUser = classFormDto.getMaxUser();
		
        for (ClassInfoDto classInfoDto : classFormDto.getClassInfos()) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setDate(classInfoDto.getDate());
            classInfo.setTime1(classInfoDto.getTime1());
            classInfo.setTime2(classInfoDto.getTime2());
            classInfo.setTime3(classInfoDto.getTime3());
            classInfo.setMaxUser(maxUser);
            classInfo.setNowUser(nowUser);
            classInfo.setOnedayClass(onedayClass);
            classInfoRepository.save(classInfo);
        }
        

        classRepository.save(onedayClass);

		
		for(int i=0; i<classImgFileList.size(); i++) {
			ClassImg classImg = new ClassImg();
			classImg.setOnedayClass(onedayClass);
			
			if(i == 0) {
				classImg.setRepimgYn("Y");	
			} else {
				classImg.setRepimgYn("N");
			}
			classImgService.saveClassImg(classImg, classImgFileList.get(i));
		}
		
		
		
		return onedayClass.getId();
	}
	
	
	@Transactional(readOnly = true)
	public ClassFormDto getClassDtl(Long classId) {
		//이미지 가져오기
		List<ClassImg> classImgList = classImgRepository.findByOnedayClassIdOrderByIdAsc(classId);
		
		List<ClassInfo> classInfoList = classInfoRepository.findByOnedayClassIdOrderByIdAsc(classId);
		
		//엔티티객체 -> Dto로 변환
		List<ClassImgDto> classImgDtoList = new ArrayList<>();
		List<ClassInfoDto> classInfoDtoList = new ArrayList<>();			
		
		for(ClassImg classImg : classImgList) {
			ClassImgDto classImgDto = ClassImgDto.of(classImg);
			classImgDtoList.add(classImgDto);
		}
		
		for(ClassInfo classInfo : classInfoList) {
			ClassInfoDto classInfoDto = ClassInfoDto.of(classInfo);
			classInfoDtoList.add(classInfoDto);
		}

		//테이블 데이터 가져오기
		OnedayClass onedayClass = classRepository.findById(classId)
												.orElseThrow(EntityNotFoundException::new);
		
		System.out.println(onedayClass.toString());
		//엔티티 -> Dto로 변환
		ClassFormDto classFormDto = ClassFormDto.of(onedayClass);
		
		//이미지 정보를 classFormDto에 넣어준다
		classFormDto.setClassImgDtoList(classImgDtoList);
		
		System.out.println(classInfoDtoList);
		classFormDto.setClassInfos(classInfoDtoList);
		
		return classFormDto;
	}
	
	
	
	//클래스 수정하기
	public Long updateClass(ClassFormDto classFormDto, Category category ,List<MultipartFile> classImgFileList)throws Exception {
		
		OnedayClass onedayClass = classRepository.findById(classFormDto.getId())
							.orElseThrow(EntityNotFoundException::new);

		onedayClass.updateClass(classFormDto, category);
		
		List<Long> classImgIds = classFormDto.getClassImgIds();
		
		for(int i = 0; i<classImgFileList.size(); i++) {
			classImgService.updateClassImg(classImgIds.get(i), classImgFileList.get(i));
		
		}
			return onedayClass.getId();
	}
	
	
	//클래스 삭제
	public void deleteClass(Long classId) {
		OnedayClass onedayClass = classRepository.findById(classId).orElseThrow(EntityNotFoundException::new);
		
		classRepository.delete(onedayClass);
	}


	@Transactional(readOnly = true)
	public Page<OnedayClass> getAdminClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		Page<OnedayClass> classPage = classRepository.getAdminOnedayClassPage(classSearchDto, pageable);
		return classPage;
	}
	
	@Transactional(readOnly = true)
	public Page<MainClassDto> getCategoryClassPage(ClassSearchDto classSearchDto, Pageable pageable) {
		
		Page<MainClassDto> categoryClassPage = classRepository.getMainClassPage(classSearchDto, pageable);
		System.out.println(categoryClassPage.toString());
		return categoryClassPage;
	}
	

	@Transactional(readOnly = true)
	public Page<MainClassDto> getMainClassPage(ClassSearchDto classSearchDto, Pageable pageable) {

		Page<MainClassDto> mainClassPage = classRepository.getMainClassPage(classSearchDto, pageable);
		
		return mainClassPage;
	}

}
