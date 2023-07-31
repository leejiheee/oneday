package com.oneday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneday.entity.ClassImg;

public interface ClassImgRepository extends JpaRepository<ClassImg, Long>{

	List<ClassImg> findByOnedayClassIdOrderByIdAsc(Long classId); 
	

	ClassImg findByOnedayClassIdAndRepimgYn(Long classId, String repimgYn);

}
