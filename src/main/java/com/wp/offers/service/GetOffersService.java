package com.wp.offers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wp.offers.data.Offer;
import com.wp.offers.repository.OfferRepository;
import com.wp.offers.util.OffersUtil;

@Service
@Scope("prototype")
public class GetOffersService extends BaseService{

	@Autowired
	protected OfferRepository offerRepo;

	@Autowired
	protected OffersUtil util;

	private Long offerId;
	private List<Offer> response;

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public List<Offer> getResponse() {
		return response;
	}
	
	protected Offer fetchOfferById() {
		Optional<Offer> optionalResult = offerRepo.findById(offerId);
		if(optionalResult.isPresent()) {
			Offer offer = optionalResult.get();
			offer.setExpired(util.checkIfOfferExpired(offer.getExpiryDate()));
			return offer;
		}
		throw new IllegalStateException("Offer not found with OfferId: " + offerId);
	}
	
	protected List<Offer> fetchAllOffers() {
		List<Offer> offerList = new ArrayList<>();
		Iterable<Offer> offers = offerRepo.findAll();
		offers.forEach(item -> {
			item.setExpired(util.checkIfOfferExpired(item.getExpiryDate()));
			offerList.add(item);
		});
		return offerList;
	}

	@Override
	protected void executeImpl() throws Exception {
		response = new ArrayList<>();
		if(offerId != null) {
			response.add(this.fetchOfferById());
		} else {
			response.addAll(this.fetchAllOffers());
		}
	}
}
