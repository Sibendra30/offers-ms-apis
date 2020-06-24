package com.wp.offers.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.wp.offers.OfferRepository;
import com.wp.offers.data.Offer;
import com.wp.offers.util.OffersUtil;

@Service
@Scope("prototype")
public class ExpireOfferService extends BaseService{
	
	@Autowired
	private OfferRepository offerRepo;
	
	@Autowired
	private OffersUtil util;
	
	private long offerId;
	private Offer response;

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	
	public Offer getResponse() {
		return response;
	}

	@Override
	protected void executeImpl() {
		Optional<Offer> optionalOffer = offerRepo.findById(offerId);
		if(optionalOffer.isPresent()) {
			Offer offer = optionalOffer.get();
			offer.setExpiryDate(new Date());
			offer.setLastUpdatedOn(new Date());
			response = offerRepo.save(offer);
			response.setExpired(util.checkIfOfferExpired(offer.getExpiryDate()));
		} else {
			throw new IllegalStateException("OfferId: "+ offerId + " is not valid");
		}
		
	}
}
