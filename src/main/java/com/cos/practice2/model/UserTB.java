package com.cos.practice2.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어) Object -> 테이블로 메핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity	// User 클래스가 Oracle에 테이블이 생성이 된다.
//@DynamicInsert	//insert시에 null인  필드를 제외시켜준다.
public class UserTB {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;	//시퀀스로 만듬
	
	@Column(nullable=false, length = 30, unique=true)
	private String username;	//아이디
	
	@Column(nullable=false, length = 100)
	private String password; 
	
	@Column(nullable=false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	//DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다. Enum을 쓰면 도메인(=어떤 범위가 정해졌다)을 만들어준다.(값을 지정해줌ex.admin,user등등)
	
	@CreationTimestamp //시간이 자동 입력(자바에서 만듬)
	private Timestamp createDate;
	
}
