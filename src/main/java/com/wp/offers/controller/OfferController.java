package com.wp.offers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wp.offers.data.Offer;
import com.wp.offers.service.CreateOfferService;
import com.wp.offers.service.ExpireOfferService;
import com.wp.offers.service.GetOffersService;

@RestController
@Scope("prototype")
@RequestMapping(value="/offers")
public class OfferController {
	
	@Autowired
	protected CreateOfferService createOfferService;
	
	@Autowired
	protected GetOffersService getOffersService;
	
	@Autowired
	protected ExpireOfferService expireOffersService; 

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Offer>> getOffers(
			@RequestParam(value="offerId", required= false) Long offerId) throws Exception {
		getOffersService.execute();
		getOffersService.setOfferId(offerId);
		List<Offer> responseBody = getOffersService.getResponse(); 
		return new ResponseEntity<List<Offer>>(responseBody, HttpStatus.OK);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<Offer> createOffer(
			@RequestBody Offer requestBody) throws Exception {
		createOfferService.setOffer(requestBody);
		createOfferService.execute();
		Offer responseBody = createOfferService.getResponse();
		return new ResponseEntity<Offer>(responseBody, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{offerId}/cancel", method=RequestMethod.DELETE)
	public ResponseEntity<Offer> expireOffer(
			@PathVariable("offerId") Long offerId) throws Exception {
		expireOffersService.setOfferId(offerId);
		expireOffersService.execute();
		Offer responseBody = expireOffersService.getResponse();
		return new ResponseEntity<Offer>(responseBody, HttpStatus.OK);
	}
}
