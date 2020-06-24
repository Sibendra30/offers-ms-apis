package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
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
		expiredOffer.setExpiryDate(new Date());
		expiredOffer.setLastUpdatedOn(new Date());
		
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
	
	private boolean checkIdOnlyDateEq(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		
	}
}
