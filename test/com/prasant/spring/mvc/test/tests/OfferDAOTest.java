package com.prasant.spring.mvc.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

import com.prasant.spring.mvc.dao.OfferDAO;
import com.prasant.spring.mvc.dao.UserDAO;
import com.prasant.spring.mvc.model.Offer;
import com.prasant.spring.mvc.model.User;

@ActiveProfiles("test")
@ContextConfiguration(locations = { 
		"classpath:com/prasant/spring/mvc/config/dao-context.xml", 
		"classpath:com/prasant/spring/mvc/test/config/dataSource.xml",
		"classpath:com/prasant/spring/mvc/config/security-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OfferDAOTest {
	
	@Autowired
	public DataSource dataSource;
	
	@Autowired
	public OfferDAO offerDao;
	
	@Autowired
	public UserDAO userDao;
	
	private User user1 = new User("grout", "grout@email.com", "password", true, "ROLE_USER", "Gayatree Rout");
	
	private Offer offer1 = new Offer(user1, "I write awesome contents.");
	
	private User user2 = new User("testuser", "testuser@email.com", "password", true, "ROLE_USER", "Test User");
	
	private Offer offer2 = new Offer(user2, "This is a test offer.");
	
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
	}

	@Test
	public void update() {
		userDao.create(user1);
		offerDao.create(offer1);
		offer1.setText("Updated text");
		offerDao.update(offer1);
		List<Offer> offers1 = offerDao.getOffersByUserName("grout");
		assertEquals("Offer retrieved should match offer created", offer1, offers1.get(0));
	}

	@Test
	public void saveOrUpdate() {
		userDao.create(user1);
		offerDao.saveOrUpdate(offer1);
		offer1.setText("Updated text");
		offerDao.saveOrUpdate(offer1);
		List<Offer> offers1 = offerDao.getOffersByUserName("grout");
		assertEquals("Offer retrieved should match offer created", offer1, offers1.get(0));
	}
	
	@Test
	public void getOffers() {
		userDao.create(user1);
		offerDao.create(offer1);
		List<Offer> offers1 = offerDao.getOffers();
		assertEquals("There should be one offer", 1, offers1.size());
		
		userDao.create(user2);
		offerDao.create(offer2);
		List<Offer> offers2 = offerDao.getOffers();
		assertEquals("There should be two offers", 2, offers2.size());
	}
	
	@Test
	public void getOffersByUserName() {
		userDao.create(user1);
		offerDao.create(offer1);
		List<Offer> offers1 = offerDao.getOffersByUserName("grout");
		assertEquals("There should be one offer", 1, offers1.size());
		assertEquals("Offer retrieved should match offer created", offer1, offers1.get(0));
	}
	
	@Test
	public void getOfferById() {
		userDao.create(user1);
		offerDao.create(offer1);
		List<Offer> offers1 = offerDao.getOffersByUserName("grout");
		int id = offers1.get(0).getId();
		Offer offer = offerDao.getOfferById(id);
		assertEquals("Offer retrieved should match offer created", offer1, offer);
	}
	
	@Test
	public void delete() {
		userDao.create(user1);
		offerDao.create(offer1);
		List<Offer> offers1 = offerDao.getOffersByUserName("grout");
		assertEquals("There should be one offer", 1, offers1.size());
		assertEquals("Offer retrieved should match offer created", offer1, offers1.get(0));
		int id = offers1.get(0).getId();
		offerDao.delete(id);
		Offer offer2 = offerDao.getOfferById(id);
		assertNull(offer2);
	}
	
}
