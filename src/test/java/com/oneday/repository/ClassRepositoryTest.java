package com.oneday.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class ClassRepositoryTest {
	@Autowired
	ClassRepository classRepository;
	
	@PersistenceContext
	EntityManager em;
	
	
}
