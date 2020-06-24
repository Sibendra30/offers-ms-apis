package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.wp.offers.MockResponseUtil;
import com.wp.offers.OfferRepository;
import com.wp.offers.data.Offer;
import com.wp.offers.util.OffersUtil;

public class GetOffersServiceTest {

	private GetOffersService getOffersService;
	private MockResponseUtil mockUtil; 
	
	@BeforeEach
	public void setup() {
		getOffersService = new GetOffersService();
		getOffersService.offerRepo = Mockito.mock(OfferRepository.class);
		getOffersService.util = Mockito.spy(OffersUtil.class);
		mockUtil = new MockResponseUtil();
	}
	
	@Test
	public void testfetchOfferById_success() throws IOException, URISyntaxException {
		Offer expectedOffer = mockUtil.getMockOffer();
		Optional<Offer> optionalResult = Optional.of(expectedOffer);
		Mockito
		.doReturn(optionalResult)
		.when(getOffersService.offerRepo).findById(1l);
		getOffersService.setOfferId(1l);
		Offer response = getOffersService.fetchOfferById();
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(expectedOffer, response);
	}
	
	@Test
	public void testfetchOfferById_failure() {
		Optional<Offer> optionalResult = Optional.empty();
		Mockito
		.doReturn(optionalResult)
		.when(getOffersService.offerRepo).findById(1l);
		getOffersService.setOfferId(1l);
		Exception ex = Assertions.assertThrows(IllegalStateException.class, () -> {
			getOffersService.fetchOfferById();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
}
