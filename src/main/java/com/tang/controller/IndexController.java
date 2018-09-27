package com.tang.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tang.model.User;
import com.tang.model.UserRepository;
import com.tang.service.BookingService;

@RestController
@RequestMapping("study")
public class IndexController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/booking")
	public String booking() {
		bookingService.bookFlight();
		return "booking flight";
	}

	@RequestMapping("/user")
	@Cacheable(value = "user-key")
	public User getUser() {
		User user = userRepository.findByUsername("ceshi");
		System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
		return user;
	}

	@RequestMapping("/uid")
	String uid(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
		if (uid == null) {
			uid = UUID.randomUUID();
		}
		session.setAttribute("uid", uid);
		return session.getId();
	}
}
