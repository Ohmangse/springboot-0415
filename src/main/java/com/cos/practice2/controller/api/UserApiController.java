package com.cos.practice2.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.practice2.dto.ResponseDto;
import com.cos.practice2.model.RoleType;
import com.cos.practice2.model.UserTB;
import com.cos.practice2.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;

	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody UserTB user) {	
		System.out.println("UserApiController : save 호출됨");
		//실제로 DB에 INSERT를 하고 아래에서 return이 되면 되요.
		
		userService.SignUpService(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value() , 1);
	}
	
	
	
	//스프링 시큐리티 이용해서 로그인!!
	//밑에 주석은 전통적인 로그인 방식(위 방식은 시큐리티 방식)
	//	@Autowired
	//	private HttpSession session; 사용해도 가능 
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody UserTB user, HttpSession session ){
	 * System.out.println("UserApiController : login호출됨"); UserTB principal =
	 * userService.LoginService(user);
	 * 
	 * if(principal != null) { session.setAttribute("principal",principal); } return
	 * new ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */

}






