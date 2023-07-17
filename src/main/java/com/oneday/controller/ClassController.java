package com.oneday.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oneday.dto.ClassFormDto;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.service.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;

@Controller
@RequiredArgsConstructor
public class ClassController {
	  private final ClassService classService;
	  
	  @GetMapping(value="/oneday/list")
	  public String classList(Model model, ClassSearchDto classSearchDto, Optional<Integer> page) {
		  Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		  
		  Page<MainClassDto> classes = classService.getMainClassDto(classSearchDto, pageable);

		  model.addAttribute("classes", classes);
		  model.addAttribute("classSearchDto", classSearchDto);
		  model.addAttribute("maxPage", 5);

	  return "/oneday/classList"; 
	 }
	 
	  
	  //클래스 등록 페이지
	  @GetMapping(value="/admin/oneday/new")
	  public String classForm(Model model) {
		  model.addAttribute("classFormDto", new ClassFormDto());

		  return "/oneday/classForm";
	  }
	  
	  //클래스 클래스 이미지 등록(insert)
	  @PostMapping(value="/admin/oneday/upload")
	  public String classNew(@Valid ClassFormDto classFormDto, BindingResult bindingResult,
			  Model model, @RequestParam("classImgFile") List<MultipartFile> classImgFileList) {
		 System.out.println(classFormDto.getClassNm() + "4444444444");
		  if(bindingResult.hasErrors()) {
			  return "oneday/classForm";
		  }
		  
		  //첫번째 이미지가 있는지 없는지 검사
		  if(classImgFileList.get(0).isEmpty() && classFormDto.getId() == null) {
			  model.addAttribute("errorMessage", "첫번째 클래스 이미지는 필수입니다.");
			  return "oneday/classForm";
		  }
		  
		  try {
			classService.saveClass(classFormDto, classImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "클래스 등록 중 에러가 발생했습니다.");
			return "oneday/classForm";
		}
		  
		  return "redirect:/";
			  
	  }
	  
	  
	  @GetMapping(value="/oneday/{classId}")
	  public String classDtl(Model model, @PathVariable("classId") Long classId) {
		  ClassFormDto classFormDto = classService.getClassDtl(classId);
		  model.addAttribute("class", classFormDto);
		  return "oneday/classDtl";
	  }
	  
	  
	  
	  
	  
}




