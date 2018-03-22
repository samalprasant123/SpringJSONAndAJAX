package com.prasant.spring.mvc.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prasant.spring.mvc.model.Offer;
import com.prasant.spring.mvc.service.OfferService;

@Controller
public class HomeController {
	
	@Autowired
	private OfferService offerService;
	
	private static Logger LOGGER = Logger.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) {
		List<Offer> offers = offerService.getOffers();
		model.addAttribute("offers", offers);
		boolean hasOffer = false;
		if (principal != null) {
			hasOffer = offerService.hasOffer(principal.getName());
		}
		model.addAttribute("hasOffer", hasOffer);
		return "home";
	}
	
}
