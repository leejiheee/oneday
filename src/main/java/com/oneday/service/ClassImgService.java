package com.oneday.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.oneday.entity.ClassImg;
import com.oneday.repository.ClassImgRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ClassImgService {
	private String classImgLocation = "C:/oneday/img";
	
	private final ClassImgRepository classImgRepository;
	
	private final FileService fileService;
	
	public void saveClassImg(ClassImg classImg, MultipartFile classImgFile) throws Exception{
		String oriImgName = classImgFile.getOriginalFilename(); //파일이름 -> 이미지1.jpg
		String imgName = "";
		String imgUrl = "";
		
		//1. 파일을 classImgLocation에 저장
		if(!StringUtils.isEmpty(oriImgName)) {
			//oriImgName 이 빈문자열이 아니라면
			imgName = fileService.uploadFile(classImgLocation, oriImgName, classImgFile.getBytes());
			imgUrl = "/images/oneday/" + imgName;
		}
		
		//2. class_img 테이블에 저장
		classImg.updateClassImg(oriImgName, imgName, imgUrl);
		classImgRepository.save(classImg);
	}
	/*
	public void updateClassImg(Long classImgId, MultipartFile classImgFile) throws Exception {
		if(!classImgFile.isEmpty()) {
			ClassImg savedClassImg = classImgRepository.findById(classImgId).orElseThrow(EntityNotFoundException::new);
			
			if(!StringUtils.isEmpty(savedClassImg.getImgName())) {
				fileService.deleteFile(classImgLocation + "/" + savedClassImg.getImgName());
			}
			
			String oriImgName = classImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(classImgLocation, oriImgName, classImgFile.getBytes());
			String imgUrl = "/imges/item" + imgName;
			
			savedClassImg.updateClassImg(oriImgName, imgName, imgUrl);
		}
	}
	*/
}
