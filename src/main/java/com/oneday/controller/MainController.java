package com.oneday.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oneday.dto.ClassSearchDto;
import com.oneday.dto.MainClassDto;
import com.oneday.service.ClassService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final ClassService classService;
	
	@GetMapping(value="/")
	public String main(Model model, ClassSearchDto classSearchDto, Optional<Integer> page) {
		  Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		  
		  Page<MainClassDto> classCt = classService.getMainClassPage(classSearchDto, pageable);
		  model.addAttribute("classes", classCt);
		  model.addAttribute("classSearchDto", classSearchDto);
		  model.addAttribute("maxPage", 5);
		
		return "main";
	}
}
