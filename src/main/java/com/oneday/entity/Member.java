package com.oneday.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.oneday.constant.Role;
import com.oneday.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Setter
@Getter
@ToString
public class Member{
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable= false, length= 255)   
	private String email;
	
	@Column(nullable= false, length= 255)
	private String name;
	
	@Column(nullable= false, length= 255)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;


	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPassword(password);
		member.setRole(Role.USER);
		
		return member;
	}
}
