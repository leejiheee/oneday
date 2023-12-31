package com.oneday.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.oneday.dto.ClassRegistDto;
import com.oneday.dto.RegistDto;
import com.oneday.dto.RegistHistDto;
import com.oneday.entity.ClassImg;
import com.oneday.entity.ClassInfo;
import com.oneday.entity.ClassTime;
import com.oneday.entity.Member;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.Regist;
import com.oneday.entity.RegistClass;
import com.oneday.repository.ClassImgRepository;
import com.oneday.repository.ClassInfoRepository;
import com.oneday.repository.ClassRepository;
import com.oneday.repository.ClassTimeRepository;
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
	private final ClassImgRepository classImgRepository;
	private final ClassTimeRepository classTimeRepository;
	
	
	//신청하기
	public Long regist(RegistDto registDto, String email) {
		//신청할 클래스를 조회
		OnedayClass onedayClass = classRepository.findById(registDto.getClassId())
											.orElseThrow(EntityNotFoundException::new);

		
		//현재 로그인한 회원 이메일을 이용해 회원정보 조회
		Member member = memberRepository.findByEmail(email);
		
		List<RegistClass> classRegistList = new ArrayList<>();
		RegistClass classRegist = RegistClass.createClassRegist(onedayClass,registDto);
		classRegistList.add(classRegist);
		
		//회원정보와 신청할 클래스 리스트 정보를 이용해 신청 엔티티 생성
		Regist regist = Regist.createRegist(member, classRegistList);
		registRepository.save(regist);
		
		//신청한 인원수만큼 현재인원이 늘어난다.
		List<ClassInfo> classInfoList = onedayClass.getClassInfos();
		for (ClassInfo classInfo : classInfoList) {
			List<ClassTime> classTimeList = classInfo.getClassTimes();
		    for(ClassTime classTime : classTimeList) {
		    	if(classTime.getTime().equals(registDto.getTime())) {
		    		classTime.removeUser(registDto.getCount());		    		
		    	}
		    }
		}


		
		return regist.getId();
		
	}
	
	@Transactional(readOnly = true)
	public Page<RegistHistDto> getRegistList(String email, Pageable pageable) {
		
		List<Regist> regists = registRepository.findRegists(email, pageable);
		
		Long totalCount = registRepository.countRegist(email);
		
		List<RegistHistDto> registHistDtos = new ArrayList<>();
		
		for(Regist regist : regists) {
			RegistHistDto registHistDto = new RegistHistDto(regist);
			List<RegistClass> registClasses = regist.getClassRegists();
			for(RegistClass registClass : registClasses) {
				ClassImg classImg = classImgRepository.findByOnedayClassIdAndRepimgYn(registClass.getOnedayClass().getId(), "Y");
				ClassRegistDto classRegistDto = new ClassRegistDto(registClass, classImg.getImgUrl());
				registHistDto.addClassRegistDto(classRegistDto);
			}
			
			registHistDtos.add(registHistDto);
			
		}
		
		return new PageImpl<RegistHistDto>(registHistDtos, pageable, totalCount);
	}
	
	
	
	public boolean validateRegist(Long registId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		Regist regist = registRepository.findById(registId).orElseThrow(EntityNotFoundException ::new);
	
		Member savedMember = regist.getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	
	//예약취소
	public void cancelRegist(Long registId) {
		Regist regist = registRepository.findById(registId)
									.orElseThrow(EntityNotFoundException::new);

		regist.cancelRegist();
		
		List<RegistClass> registClassList = regist.getClassRegists();
		for(RegistClass registClass : registClassList) {
			List<ClassInfo> classInfoList = registClass.getOnedayClass().getClassInfos();
			for(ClassInfo classInfo : classInfoList) {
				List<ClassTime> classTimeList = classInfo.getClassTimes();
				for(ClassTime classTime : classTimeList) {
					if(classTime.getTime().equals(registClass.getTime())) {
						classTime.addUser(registClass.getCount());
					}
				}
			}
			
		}
		
	}
	
	//예약내역 삭제
	public void deleteRegist(Long registId) {
		Regist regist = registRepository.findById(registId)
				.orElseThrow(EntityNotFoundException::new);
		
		registRepository.delete(regist);
	}
	
}
