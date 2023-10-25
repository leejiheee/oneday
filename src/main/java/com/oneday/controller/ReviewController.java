package com.oneday.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.oneday.dto.ReviewDto;
import com.oneday.service.RegistService;
import com.oneday.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	//리뷰 등록
	@PostMapping(value = "/review/{classId}")
	public String saveReview(@Valid @ModelAttribute ReviewDto reviewDto, BindingResult bindingResult,
			Model model, Principal principal, @PathVariable("classId") Long classId) {
		String userName = principal.getName();
		
		reviewService.saveReview(userName, reviewDto, classId);

	    
	    return "redirect:/regists";
	}
}
