package com.prasant.spring.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.prasant.spring.mvc.dao.MessagesDAO;
import com.prasant.spring.mvc.dao.UserDAO;
import com.prasant.spring.mvc.model.Message;
import com.prasant.spring.mvc.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private MessagesDAO messagesDAO;
	
	public void createUser(User user) {
		userDao.create(user);
	}

	public boolean userExists(String username) {
		return userDao.userExists(username);
	}

	@Secured("ROLE_ADMIN")
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	
	public void sendMessage(Message message) {
		messagesDAO.saveOrUpdate(message);
	}
	
	public User getUser(String username) {
		return userDao.getUser(username);
	}

}
