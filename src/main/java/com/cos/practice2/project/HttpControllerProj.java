package com.cos.practice2.project;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청에 대한 응답(HTML파일)
//@Controller
//@RequestBody에 데이터를 실어서 보내면 spring에서 간단하게 오브젝트로 매핑해서 받을 수 있다.
//사용자가 요청에 대한 응답(Data를 응답해줌)
@RestController
public class HttpControllerProj {
	
	private static final String TAG = "HttpControllerProj : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter : "+m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter : "+m.getId());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저의 요청은 무조건 get요청밖에 할 수 있다.
	//http://localhost:8000/practice/http/get (select)
	@GetMapping("/http/get")
	//한개씩 받는 방법
	//public String getTest(@RequestParam int id, @RequestParam String username) {
//	한꺼번에 받는 방법
		public String getTest(Member m) {	//id=1&username=ssar&password=1234&email=ssar@nate.com
		return "get 요청: " + m.getId()+","+ m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/post (insert)
	//바디 데이터는 @RequestBody로 받아야 한다.(텍스트 형식)
	@PostMapping("/http/post")
//	public String postTest(@RequestBody String text) {
//		return "post 요청: " + text;	//*text 형식으로 받을 때 사용.*
//	}
	public String postTest(@RequestBody Member m) {	//*JSON형식으로 받을 때 사용*
		return "post 요청: " + m.getId()+","+ m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청: "+ m.getId()+","+ m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
