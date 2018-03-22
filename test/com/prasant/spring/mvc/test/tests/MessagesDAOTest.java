package com.prasant.spring.mvc.test.tests;

import static org.junit.Assert.assertEquals;

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

import com.prasant.spring.mvc.dao.MessagesDAO;
import com.prasant.spring.mvc.dao.OfferDAO;
import com.prasant.spring.mvc.dao.UserDAO;
import com.prasant.spring.mvc.model.Message;
import com.prasant.spring.mvc.model.Offer;
import com.prasant.spring.mvc.model.User;

@ActiveProfiles("test")
@ContextConfiguration(locations = { 
		"classpath:com/prasant/spring/mvc/config/dao-context.xml", 
		"classpath:com/prasant/spring/mvc/test/config/dataSource.xml",
		"classpath:com/prasant/spring/mvc/config/security-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MessagesDAOTest {

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public MessagesDAO messagesDAO;	
	
	@Autowired
	public OfferDAO offerDao;
	
	@Autowired
	public UserDAO userDao;
	
	private User user = new User("psamal", "psamal@email.com", "password", true, "ROLE_ADMIN", "Prasant Samal");
	private User user1 = new User("grout", "grout@email.com", "password", true, "ROLE_USER", "Gayatree Rout");
	private User user2 = new User("testuser", "testuser@email.com", "password", true, "ROLE_USER", "Test User");
	
	private Offer offer1 = new Offer(user1, "I write awesome contents.");
	private Offer offer2 = new Offer(user2, "This is a test offer.");
	
	private Message message1 = new Message("Test message 1", "Test content 1", "TestName1", "test1@email.com", user1.getUsername());
	private Message message2 = new Message("Test message 2", "Test content 2", "TestName2", "test2@email.com", user2.getUsername());
	
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
		offerDao.create(offer1);
		messagesDAO.create(message1);
		List<Message> messages = messagesDAO.getMessages();
		assertEquals("Should be 1 message", 1, messages.size());
		assertEquals("Retrieved should be equal to created", message1, messages.get(0));
	}
	
	@Test
	public void update() {
		userDao.create(user2);
		offerDao.create(offer2);
		message2.setContent("Test content 2 updated");
		messagesDAO.create(message2);
		List<Message> messages = messagesDAO.getMessages();
		assertEquals("Retrieved should be equal to created", message2, messages.get(0));
	}
	
	@Test
	public void saveOrUpdate() {
		userDao.create(user1);
		offerDao.create(offer1);
		messagesDAO.create(message1);
		List<Message> messages1 = messagesDAO.getMessages();
		assertEquals("Should be 1 message", 1, messages1.size());
		assertEquals("Retrieved should be equal to created", message1, messages1.get(0));
		userDao.create(user2);
		offerDao.create(offer2);
		messagesDAO.create(message2);
		message2.setContent("Test content 2 updated");
		messagesDAO.saveOrUpdate(message2);
		List<Message> messages2 = messagesDAO.getMessages("testuser");
		assertEquals("Should be 1 message", 1, messages2.size());
		assertEquals("Retrieved should be equal to created", message2, messages2.get(0));
		messagesDAO.delete(messages2.get(0).getId());
		List<Message> messages3 = messagesDAO.getMessages("testuser");
		assertEquals("Should be 0 message", 0, messages3.size());
	}
	
}
