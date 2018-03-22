package com.prasant.spring.mvc.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prasant.spring.mvc.model.Message;

@Transactional
@Component("messagesDao")
public class MessagesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public void create(Message message) {
		session().save(message);
	}
	
	public void update(Message message) {
		session().update(message);
	}
	
	public void saveOrUpdate(Message message) {
		session().saveOrUpdate(message);
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getMessages() {
		return session().createQuery("from Message").list();
	}
	
	public List<Message> getMessages(String username) {
		List<Message> messages = null;
		CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> messageRoot = criteriaQuery.from(Message.class);
		criteriaQuery.select(messageRoot).where(criteriaBuilder.equal(messageRoot.get("username"), username));
		
		Query<Message> query = session().createQuery(criteriaQuery);
		messages = query.getResultList();
		return messages;
	}
	
	public void delete(int id) {
		Message message = null;
		message = getMessageById(id);
		if (message != null) {
			session().delete(message);
		}		
	}

	private Message getMessageById(int id) {
		CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
		CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
		Root<Message> messageRoot = criteriaQuery.from(Message.class);
		criteriaQuery.select(messageRoot).where(criteriaBuilder.equal(messageRoot.get("id"), id));
		
		Query<Message> query = session().createQuery(criteriaQuery);
		Message message = query.uniqueResult();
		return message;
	}
}
