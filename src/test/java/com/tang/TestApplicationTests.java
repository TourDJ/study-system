package com.tang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tang.jdbc.UserRepository;
import com.tang.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private UserRepository userRepository;


	@Test
	public void findAllUsers() {
		List<User> users = userRepository.findAll();
		assertNotNull(users);
		assertTrue(!users.isEmpty());

	}

	@Test
	public void findUserById() {
		User user = userRepository.findUserById(1);
		assertNotNull(user);
	}

	@Test
	public void createUser() {
		User user = new User("tom", "tom@gmail.com");
		User savedUser = userRepository.create(user);
		User newUser = userRepository.findUserById(savedUser.getId());
		assertEquals("tom", newUser.getUserid());
		assertEquals("tom@gmail.com", newUser.getUsername());
	}
	
	@Test
	public void updateById(Integer id) {
		User newUser = new User("JackChen", "JackChen@qq.com");
		userRepository.update(newUser);
		User newUser2 = userRepository.findUserById(newUser.getId());
		assertEquals(newUser.getUserid(), newUser2.getUserid());
		assertEquals(newUser.getUsername(), newUser2.getUsername());
	}

	@Test
	public void deleteUser() {
		userRepository.delete(1);
	}

}
