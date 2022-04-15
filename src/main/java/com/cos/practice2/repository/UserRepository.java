package com.cos.practice2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.practice2.model.UserTB;

//DAO
//자동으로 bean등록이 된다.
//@Repository //생량 가능하다.
public interface UserRepository extends JpaRepository<UserTB, Integer> {
	//SELECT * FROM usertb WHERE username=1?;
	Optional<UserTB> findByUsername(String username);
}

//JPA Naming 전략
	//select * from userTB where username=? and password=?
	//UserTB findByUsernameAndPassword(String username, String password);
	
	//	@Query(value="select * from userTB where username=?1 and password=?2", nativeQuery = true)
	//	UserTB login(String username, String password);