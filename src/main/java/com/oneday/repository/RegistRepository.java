package com.oneday.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oneday.entity.Regist;

public interface RegistRepository extends JpaRepository<Regist, Long>{
	
	//현재 로그인한 사용자의 신청 데이터를 페이징 조건에 맞춰서 조회
	@Query("select o from Regist o where o.member.email = :email order by o.registDate desc")
	List<Regist> findRegists(@Param("email") String email, Pageable pageable);

	//현재 로그인한 회원의 신청 개수가 몇개인지 조회
	@Query("select count(o) from Regist o where o.member.email = :email")
	Long countRegist(@Param("email")String email);


}
