package com.oneday.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oneday.dto.MemberFormDto;
import com.oneday.entity.Member;
import com.oneday.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	
	//로그인 화면
	@GetMapping(value="/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	//회원가입 화면
	@GetMapping(value="/members/regist")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto()); {
			return "member/memberForm";						
		}
	}
	
	//회원가입
	@PostMapping(value="/members/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
						
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		
		}
		
		return "redirect:/";
	}
	
	//로그인 실패했을 때
	@GetMapping(value="/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
}
