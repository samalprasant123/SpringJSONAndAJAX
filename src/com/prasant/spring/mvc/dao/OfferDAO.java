package com.prasant.spring.mvc.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prasant.spring.mvc.model.Offer;
import com.prasant.spring.mvc.model.User;

@Transactional
@Component("offerDao")
public class OfferDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<Offer> getOffers() {
		List<Offer> offers = null;
		CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
		CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
		Root<Offer> offerRoot = criteriaQuery.from(Offer.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		criteriaQuery.select(offerRoot).where(
				criteriaBuilder.equal(offerRoot.get("user"), userRoot.get("username")), 
				criteriaBuilder.equal(userRoot.get("enabled"), true));
		Query<Offer> query = session().createQuery(criteriaQuery);
		offers = query.getResultList();
		return offers;
	}
	
	public Offer getOfferById(int id) {
		Offer offer = null;
		try {
			CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
			CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
			Root<Offer> offerRoot = criteriaQuery.from(Offer.class);
			criteriaQuery.select(offerRoot).where(criteriaBuilder.equal(offerRoot.get("id"), id));
			Query<Offer> query = session().createQuery(criteriaQuery);
			offer = query.getSingleResult();
			return offer;
		} catch (NoResultException e) {
			return null;
		}		
	}
	
	public List<Offer> getOffersByUserName(String username) {
		List<Offer> offers = null;
		CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
		CriteriaQuery<Offer> criteriaQuery = criteriaBuilder.createQuery(Offer.class);
		Root<Offer> offerRoot = criteriaQuery.from(Offer.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		criteriaQuery.select(offerRoot).where(
				criteriaBuilder.equal(offerRoot.get("user"), userRoot.get("username")), 
				criteriaBuilder.equal(userRoot.get("enabled"), true));
		criteriaQuery.select(offerRoot).where(criteriaBuilder.equal(offerRoot.get("user").get("username"), username));
		Query<Offer> query = session().createQuery(criteriaQuery);
		offers = query.getResultList();
		return offers;
	}
	
	public void delete(int id) {
		Offer offer = null;
		offer = getOfferById(id);
		if (offer != null) {
			session().delete(offer);
		}		
	}
	
	public void create(Offer offer) {
		session().save(offer);
	}
	
	public void update(Offer offer) {
		session().update(offer);
	}
	
	public void saveOrUpdate(Offer offer) {
		session().saveOrUpdate(offer);
	}

}
