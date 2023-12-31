package com.oneday.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;

import com.oneday.dto.RegistDto;
import com.oneday.dto.RegistHistDto;
import com.oneday.service.RegistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistController {
	private final RegistService registService;
	
	@PostMapping(value = "/regist")
	public @ResponseBody ResponseEntity regist(@RequestBody @Valid RegistDto registDto, BindingResult bindingResult, Principal principal) {
		//Principal : 로그인한 사용자의 정보를 가져올 수 있다.
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName();
		Long registId;
		
		try {
			registId = registService.regist(registDto, email);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
			return new ResponseEntity<Long>(registId, HttpStatus.OK);
		
	}
	
	@GetMapping(value = {"/regists", "/regists/{page}"})
	public String registHist(@PathVariable("page") Optional<Integer>page, Principal principal, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		Page<RegistHistDto> registHistDto = registService.getRegistList(principal.getName(), pageable);
		
		model.addAttribute("regists", registHistDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("page", pageable.getPageNumber());
		
		return "my/myPage";
		
	}
	
	@PostMapping("/regist/{registId}/cancel")
	public @ResponseBody ResponseEntity cancelRegist(@PathVariable("registId") Long registId, Principal principal) {
		//예약 취소 권한 확인
		if(!registService.validateRegist(registId, principal.getName())) {
			return new ResponseEntity<String>("예약 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		//예약취소
		registService.cancelRegist(registId);
		return new ResponseEntity <Long>(registId, HttpStatus.OK);
		
		
	}
	
	
	//예약내역삭제
	@DeleteMapping("/regist/{registId}/delete")
	public @ResponseBody ResponseEntity deleteRegist(@PathVariable("registId")Long registId, Principal principal) {
		//권한 확인
		if(!registService.validateRegist(registId, principal.getName())) {
			return new ResponseEntity<String>("예약내역 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		registService.deleteRegist(registId);
		
		return new ResponseEntity<Long>(registId, HttpStatus.OK);
	}
	
	
}
