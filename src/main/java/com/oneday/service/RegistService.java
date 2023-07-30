package com.oneday.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oneday.dto.RegistDto;
import com.oneday.entity.Member;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.Regist;
import com.oneday.entity.RegistClass;
import com.oneday.repository.ClassRepository;
import com.oneday.repository.MemberRepository;
import com.oneday.repository.RegistRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistService {
	private final ClassRepository classRepository;
	private final MemberRepository memberRepository;
	private final RegistRepository registRepository;
	
	
	//신청하기
	public Long regist(RegistDto registDto, String email) {
		
		//신청할 클래스를 조회
		OnedayClass onedayClass = classRepository.findById(registDto.getClassId())
											.orElseThrow(EntityNotFoundException::new);
	
		//현재 로그인한 회원 이메일을 이용해 회원정보 조회
		Member member = memberRepository.findByEmail(email);
		
		List<RegistClass> classRegistList = new ArrayList<>();
		RegistClass classRegist = RegistClass.createClassRegist(onedayClass, null, registDto.getCount());
		classRegistList.add(classRegist);
		
		//회원정보와 신청할 클래스 리스트 정보를 이용해 신청 엔티티 생성
		Regist regist = Regist.createRegist(member, classRegistList);
		registRepository.save(regist);
		
		return regist.getId();
		
	}
	
	
	
}
