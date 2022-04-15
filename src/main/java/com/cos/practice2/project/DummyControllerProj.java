package com.cos.practice2.project;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.practice2.model.RoleType;
import com.cos.practice2.model.UserTB;
import com.cos.practice2.repository.UserRepository;

//html 파일이 아니라 data를 ㄹ턴해주는 controller = RestController
@RestController
public class DummyControllerProj {
	
	@Autowired	//의존성 주입
	private UserRepository userRepository;
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해요.
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제 되었습니다. ID : "+id;
	}
	
	//email,password -> 수정
	@Transactional	//함수 종료시에 자동 commit이 됨
	@PutMapping("/dummy/user/{id}")
	public UserTB updateUser(@PathVariable int id, @RequestBody UserTB requestUserTB) {	//json 데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줘요..)
		System.out.println("id : "+id);
		System.out.println("password : "+requestUserTB.getPassword());
		System.out.println("email : "+requestUserTB.getEmail());
		
		UserTB user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUserTB.getPassword());
		user.setEmail(requestUserTB.getEmail());
		
//		userRepository.save(user);
		
		//더티 체킹(@Transactional)
		//상태 변경 검사이다.
		//JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스 반영한다.
		//그렇기 때문에 값을 변경한 뒤, save 하지 않더라도 DB에 반영되는 것이다.
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<UserTB> list(){
		return userRepository.findAll();
	}
	//한페이지당 2건의 데이터를 받아올 예정
	@GetMapping("/dummy/user")	//size => 페이지당 가지고 오는 데이터 갯수
	public List<UserTB> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){
		Page<UserTB> pagingUser = userRepository.findAll(pageable);
		
		List<UserTB> users = pagingUser.getContent();
		return users;
	}
	
	 
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/practice/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public UserTB detail(@PathVariable int id) {
		// user/4 을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return null 이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
		//Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
		
		//람다식
//		UerTB user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다.");
//		})
		UserTB user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다.");
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}
	
	
	//http://localhost:8000/practice/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(UserTB user) {	//key=value (약속된 규칙)
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료 되었습니다.";
	}
}
