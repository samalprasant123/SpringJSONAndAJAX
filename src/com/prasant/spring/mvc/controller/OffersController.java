package com.prasant.spring.mvc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.prasant.spring.mvc.model.Offer;
import com.prasant.spring.mvc.service.OfferService;
import com.prasant.spring.mvc.service.UserService;
import com.prasant.spring.mvc.validationgroup.FormValidationGroup;

@Controller
public class OffersController {
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/offers")
	public String showOffers(Model model) {
		List<Offer> offers = offerService.getOffers();
		model.addAttribute("offers", offers);
		return "offers";
	}
	
	@RequestMapping("/createoffer")
	public String createOffer(Model model, Principal principal) {
		Offer offer = null;
		if (principal != null && principal.getName() != null) {
			offer = offerService.getOffers(principal.getName());
		}
		
		if (offer == null) {
			offer = new Offer();
		}
		
		model.addAttribute("offer", offer);
		return "createoffer";
	}
	
	@RequestMapping(value="/docreate", method=RequestMethod.POST)
	public String offerCreated(Model model, @Validated(FormValidationGroup.class) Offer offer, BindingResult bindingResult, Principal principal,
			@RequestParam(value="save", required = false) String actionSave,
			@RequestParam(value="delete", required = false) String actionDelete) {
		if (bindingResult.hasErrors()) {
			return "createoffer";
		}
		if (actionSave != null && actionSave.equals("Save Offer")) {
			String username = principal.getName();
			offer.getUser().setUsername(username);
			offerService.saveOrUpdate(offer);
			return "offercreated";
		} else if (actionDelete != null && actionDelete.equals("Delete Offer")) {
			offerService.deleteOffer(offer.getId());
			return "offerdeleted";
		} else {
			return "createoffer";
		}
	}
	
}
