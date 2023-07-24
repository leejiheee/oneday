package com.oneday.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.oneday.constant.ClassEnd;
import com.oneday.dto.ClassFormDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="oneday_class")
@Setter
@Getter
@ToString
public class OnedayClass{
	
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String classNm;
		
	@Column(nullable = false, length = 50)
	private String teacherNm;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String classDetail;
	
	@Column(nullable = false)
	private int price;	

	@Column(nullable = false, length = 255)
	private String region;
	
	@Column(nullable = false, length = 255)
	private String regionDtl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Category category;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "onedayClass")
    private List<Schedule> schedules;
    
    private List<String> dates; // 스케줄 날짜 리스트
    private List<String> times; // 스케줄 시간 리스트

    // Getter와 Setter 메서드들

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

	
	@Enumerated(EnumType.STRING)
	private ClassEnd classEnd; //마감 상태
	
	//class엔티티 수정
	public void updateClass(ClassFormDto classFormDto, Category category) {
		this.classNm = classFormDto.getClassNm();
		this.price = classFormDto.getPrice();
		this.teacherNm = classFormDto.getTeacherNm();
		this.region = classFormDto.getRegion();
		this.regionDtl = classFormDto.getRegionDtl();
		this.classDetail = classFormDto.getClassDetail();
		this.classEnd = classFormDto.getClassEnd();
		this.category = category;
		
        // 스케줄 업데이트 로직 추가
        List<Schedule> updatedSchedules = new ArrayList<>();
        List<String> dates = classFormDto.getDates();
        List<String> times = classFormDto.getTimes();

        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            String time = times.get(i);
            Schedule schedule = new Schedule(date, time);
            schedule.setOnedayClass(this); // Schedule과 Class 엔티티간의 양방향 관계 설정
            updatedSchedules.add(schedule);
        }

        this.setSchedules(updatedSchedules);
	}
	
	public static OnedayClass createClass(Category category) {
		OnedayClass onedayClass = new OnedayClass();
		onedayClass.setCategory(category);
		
		return onedayClass;
	}

    
    public void addSchedule(Schedule schedule) {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        schedules.add(schedule);
    }
	
}
