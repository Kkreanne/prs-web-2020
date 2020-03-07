package prs.web;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import prs.web.user.User;
import prs.web.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

	@Autowired
	UserRepository userRepo;
	
	@Test
	public void testFindAll() {
		List<User> users = userRepo.findAll();
		assertNotNull(users);
		assertTrue(users.size()>0);
	}
	
	@Test
	public void testUserAdd() {
		User u = new User("tUser", "pwd", "Test", "User", "513-111-1212", "test@test.com", true, false);
		assertNotNull(userRepo.save(u));
	}
	
	//could write methods for get, update, insert & delete	
}
