package com.prasant.spring.mvc.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.prasant.spring.mvc.dao.OfferDAO;
import com.prasant.spring.mvc.model.Offer;

@Service("offerService")
public class OfferService {
	
	@Autowired
	private OfferDAO offerDao;
	
	public List<Offer> getOffers() {
		return offerDao.getOffers();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void createOffer(Offer offer) {
		offerDao.create(offer);
	}

	public void throwTestException() {
		offerDao.getOfferById(999);		
	}

	public boolean hasOffer(String username) {
		if (username == null) {
			return false;
		}
		List<Offer> offers = offerDao.getOffersByUserName(username);
		if (offers.size() == 0) {
			return false;
		}
		return true;
	}
	
	public Offer getOffers(String username) {
		List<Offer> offers = null;
		offers = offerDao.getOffersByUserName(username);
		if (offers != null && !offers.isEmpty()) {
			return offers.get(0);
		}
		return null;
	}

	public void saveOrUpdate(@Valid Offer offer) {
		if (offer.getId() != 0) {
			offerDao.update(offer);
		} else {
			offerDao.create(offer);
		}		
	}

	public void deleteOffer(int offerId) {
		offerDao.delete(offerId);
	}

}
