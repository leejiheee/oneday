package com.oneday.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oneday.dto.ClassFormDto;
import com.oneday.dto.ClassInfoDto;
import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.entity.Category;
import com.oneday.entity.OnedayClass;
import com.oneday.service.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Controller
public class ClassController {

		private final ClassService classService;
		
		@Autowired
	    public ClassController(ClassService classService) {
	        this.classService = classService;
	    }
	  
	  //클래스 리스트
	  @GetMapping(value= "/oneday/list")
	  public String classList(Model model, ClassSearchDto classSearchDto, Optional<Integer> page) {
		  Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		  
		  Page<MainClassDto> classes = classService.getMainClassPage(classSearchDto, pageable);
		  model.addAttribute("classes", classes);
		  model.addAttribute("classSearchDto", classSearchDto);
		  model.addAttribute("maxPage", 5);

	  return "/oneday/classList"; 
	 }

	  @GetMapping(value = "/oneday/list/1")
	  public String categoryClass(Model model, ClassSearchDto classSearchDto, Optional<Integer> page) {
		  Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		  System.out.println("여기 오긴함?????");
		  Page<MainClassDto> classCt = classService.getCategoryClassPage(classSearchDto, pageable);
		  System.out.println("그럼 여기는????????");
		  model.addAttribute("classCt", classCt);
		  model.addAttribute("classSearchDto", classSearchDto);
		  model.addAttribute("maxPage", 5);
		  
		  return "/oneday/categoryClass";
	  }
	  

	  //클래스 등록 페이지
	  @GetMapping(value="/admin/oneday/new")
	  public String classForm(Model model) {
		  model.addAttribute("classFormDto", new ClassFormDto());

		  return "/oneday/classForm";
	  }
	  
	  //클래스 클래스 이미지 등록(insert)
	  @PostMapping(value="/admin/oneday/upload")
	  public String classNew(@Valid @ModelAttribute ClassFormDto classFormDto, BindingResult bindingResult,
			  Model model, @RequestParam("classImgFile") List<MultipartFile> classImgFileList) {
		  
			
			  if (bindingResult.hasErrors()) { System.out.println("입력값이 유효하지 않습니다.");
			  for(FieldError error : bindingResult.getFieldErrors()) {
			  System.out.println("Field: " + error.getField() + ", Message: " +
			  error.getDefaultMessage()); } return "oneday/classForm"; }
			 
		  
		  //첫번째 이미지가 있는지 없는지 검사
		  if(classImgFileList.get(0).isEmpty() && classFormDto.getId() == null) {
			  model.addAttribute("errorMessage", "첫번째 클래스 이미지는 필수입니다.");
			  return "oneday/classForm";
		  }
		  System.out.println(classFormDto.getClassDetail() + "FDFF");
		  
		  for(ClassInfoDto classInfo : classFormDto.getClassInfos()) {
			  System.out.println(classInfo.getDate() + "JJJ");
			  
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
		  
		  System.out.println(classFormDto.getClassInfos() + "!~!~!~!~!~!~!~!~!~!~!~!");
		  for(ClassInfoDto test : classFormDto.getClassInfos()) {
			  System.out.println(test.getDate() +"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		  }
		  return "oneday/classDtl";
	  }
	  
	  //관리페이지
	  @GetMapping(value= {"/admin/classes", "/admin/classes/{page}"})
	  public String classManage(ClassSearchDto classSearchDto, @PathVariable("page")
	  Optional<Integer> page, Model model) {
		  Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
		  
		  Page<OnedayClass> classes = classService.getAdminClassPage(classSearchDto, pageable);
		  
		  model.addAttribute("classes", classes);
		  model.addAttribute("classSearchDto", classSearchDto);
		  model.addAttribute("maxPage", 5);
		  
		  return "/oneday/classMng";
	  }
	  
	  
	  //수정페이지
	  @GetMapping(value = "/admin/oneday/{classId}")
	  public String classDtl(@PathVariable("classId") Long classId, Model model) {
		  try {
			  ClassFormDto classFormDto = classService.getClassDtl(classId);
			  model.addAttribute("classFormDto", classFormDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "클래스 정보를 가져올때 에러가 발생했습니다.");
			
			model.addAttribute("classFormDto", new ClassFormDto());
			return "oneday/classForm";
		}
		  
		  return "oneday/classModifyForm";
		  
	  }
	  
	  //클래스 수정(update)
	  @PostMapping(value = "/admin/oneday/{classId}")
	  public String classUpdate(@Valid ClassFormDto classFormDto, Category category, Model model,
			  BindingResult bindingResult, @RequestParam("classImgFile") List<MultipartFile> classImgFileList) {
		  
		  if(bindingResult.hasErrors()) {
			  return "oneday/classForm";
		  }
		  
		  if(classImgFileList.get(0).isEmpty() && classFormDto.getId() == null) {
			  model.addAttribute("errorMessage", "첫번째 클래스 이미지는 필수입니다.");
			  return "oneday/classForm";
		  }
		  
		  try {
			classService.updateClass(classFormDto, category, classImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "클래스 수정 중 에러가 발생했습니다.");
			return "oneday/classForm";
		}
		  
		  return "redirect:/";
	  }
	  

	  
		//클래스 삭제
		@DeleteMapping("/admin/{classId}/delete")
		public @ResponseBody ResponseEntity deleteClass(@PathVariable("classId")Long classId, Principal principal) {
			classService.deleteClass(classId);
			
			return new ResponseEntity<Long>(classId, HttpStatus.OK);
		}
}




