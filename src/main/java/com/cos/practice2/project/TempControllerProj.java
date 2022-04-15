package com.cos.practice2.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerProj {

		@GetMapping("/temp/home")
		public String tempHome() {
			System.out.println("temphome()");
			return "home.html";
		}
}
