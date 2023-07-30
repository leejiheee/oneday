package com.oneday.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.FieldError;

import com.oneday.dto.RegistDto;
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
}
