package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.wp.offers.MockResponseUtil;
import com.wp.offers.data.Offer;
import com.wp.offers.repository.OfferRepository;
import com.wp.offers.util.OffersUtil;

public class ExpireOfferServiceTest {

	private ExpireOfferService expireOfferService;
	private MockResponseUtil mockUtil;
	
	@BeforeEach
	public void setup() {
		mockUtil = new MockResponseUtil();
		expireOfferService = new ExpireOfferService();
		expireOfferService.util = new OffersUtil();
		expireOfferService.offerRepo = Mockito.mock(OfferRepository.class);
	}
	
	@Test
	public void testExpireOffer_success() throws IOException, URISyntaxException {
		Offer inputOffer = mockUtil.getMockOffer();
		Optional<Offer> optionalResult = Optional.of(inputOffer);
		
		Offer expiredOffer = inputOffer.clone();
		expiredOffer.setExpiryDate(LocalDateTime.now());
		expiredOffer.setLastUpdatedOn(LocalDateTime.now());
		
		Mockito
			.doReturn(expiredOffer)
			.when(expireOfferService.offerRepo).save(inputOffer);
		
		Mockito
		.doReturn(optionalResult)
		.when(expireOfferService.offerRepo).findById(1l);
		
		expireOfferService.setOfferId(1l);
		expireOfferService.executeImpl();
		
		Assertions.assertTrue(this.checkIdOnlyDateEq(
				expireOfferService.getResponse().getExpiryDate(), 
				expiredOffer.getExpiryDate()));
		
		Assertions.assertTrue(this.checkIdOnlyDateEq(
				expireOfferService.getResponse().getLastUpdatedOn(), 
				expiredOffer.getLastUpdatedOn()));
		
		Assertions.assertFalse(expireOfferService.getResponse().isExpired());
	}
	
	@Test
	public void testExpireOffer_invalidOfferId() {
		Optional<Offer> optionalResult = Optional.empty();
		Mockito
		.doReturn(optionalResult)
		.when(expireOfferService.offerRepo).findById(1l);
		expireOfferService.setOfferId(1l);
		Exception ex = Assertions.assertThrows(IllegalStateException.class, () -> {
			expireOfferService.executeImpl();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	private boolean checkIdOnlyDateEq(LocalDateTime date1, LocalDateTime date2) {
		
		return date1.getYear() == date1.getYear()
				&& date1.getMonthValue() == date2.getMonthValue()
				&& date1.getDayOfMonth() == date2.getDayOfMonth();
		
	}
}
