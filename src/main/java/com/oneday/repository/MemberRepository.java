package com.oneday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneday.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	//select * from member where email = ?
	Member findByEmail(String email);
}
