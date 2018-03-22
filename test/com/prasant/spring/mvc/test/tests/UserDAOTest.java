package com.prasant.spring.mvc.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.prasant.spring.mvc.dao.UserDAO;
import com.prasant.spring.mvc.model.User;

@ActiveProfiles("test")
@ContextConfiguration(locations = { 
		"classpath:com/prasant/spring/mvc/config/dao-context.xml", 
		"classpath:com/prasant/spring/mvc/test/config/dataSource.xml",
		"classpath:com/prasant/spring/mvc/config/security-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOTest {
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public UserDAO userDao;
	
	private User user1 = new User("grout", "grout@email.com", "password", true, "ROLE_USER", "Gayatree Rout");
	
	private User user2 = new User("psamal", "psamal@email.com", "password", true, "ROLE_ADMIN", "Prasant Samal");
	
	@Before
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("DELETE FROM offer");
		jdbcTemplate.execute("DELETE FROM messages");
		jdbcTemplate.execute("DELETE FROM users");
	}
	
	@Test
	public void create() {		
		userDao.create(user1);
	}
	
	@Test
	public void getUsers() {
		userDao.create(user1);
		userDao.create(user2);
		List<User> users = userDao.getUsers();
		assertEquals("2 users currently", 2, users.size());
	}
	
	@Test
	public void getUser() {
		userDao.create(user2);
		assertTrue("User psamal exists", userDao.userExists("psamal"));
		assertFalse("User doenst psamal1 exists", userDao.userExists("psamal1"));
		List<User> users = userDao.getUsers();
		assertEquals("Created user and 	retrieved User should be identical", user2, users.get(0));
	}

}
