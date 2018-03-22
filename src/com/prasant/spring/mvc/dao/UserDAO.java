package com.prasant.spring.mvc.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prasant.spring.mvc.model.User;

@Transactional
@Component("userDao")
public class UserDAO {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}

	public boolean userExists(String username) {
		return getUser(username) != null;
	}
	
	public User getUser(String username) {
		CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("username"), username));
		Query<User> query = session().createQuery(criteriaQuery);
		User user = query.uniqueResult();
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return session().createQuery("from User").list();
	}
}
