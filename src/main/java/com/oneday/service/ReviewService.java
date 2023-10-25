package com.oneday.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oneday.dto.ReviewDto;
import com.oneday.entity.Member;
import com.oneday.entity.OnedayClass;
import com.oneday.entity.Regist;
import com.oneday.entity.Review;
import com.oneday.repository.ClassImgRepository;
import com.oneday.repository.ClassRepository;
import com.oneday.repository.ClassTimeRepository;
import com.oneday.repository.MemberRepository;
import com.oneday.repository.RegistRepository;
import com.oneday.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
	private final ClassRepository classRepository;
	private final MemberRepository memberRepository;
	private final ReviewRepository reviewRepository;
	private final RegistRepository registRepository;

	
	public Long saveReview(String userName, ReviewDto reviewDto, Long classId) {
		OnedayClass onedayClass = classRepository.findById(classId)
				.orElseThrow(EntityNotFoundException::new);
		
		Regist regist = registRepository.findById(reviewDto.getRegistId())
				.orElseThrow(EntityNotFoundException::new);
		Member member = memberRepository.findByEmail(userName);
		
		Review review = new Review();
	    review.setReviewScore(reviewDto.getReviewScore());
	    review.setReviewContent(reviewDto.getReviewContent());
	    
	    review.setRegist(regist);
	    review.setMember(member);
	    review.setOnedayClass(onedayClass);
	    
	    regist.reviewRegist();
	    reviewRepository.save(review);
	    updateClassAverageRating(onedayClass);
	    
	    return review.getId();
	    
	}
	
	private void updateClassAverageRating(OnedayClass onedayClass) {
	    // 원데이 클래스의 모든 리뷰를 가져옵니다.
	    List<Review> reviews = reviewRepository.findByOnedayClass_Id(onedayClass.getId());

	    // 리뷰 평점의 평균을 계산
	    double sum = reviews.stream().mapToDouble(Review::getReviewScore).sum();
	    double averageRating = sum / reviews.size();

	    // 계산된 평균을 원데이 클래스에 저장
	    onedayClass.setAverageRating(averageRating);
	    classRepository.save(onedayClass);
	}
	

	
}
