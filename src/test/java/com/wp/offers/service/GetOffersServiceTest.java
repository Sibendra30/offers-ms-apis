package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.wp.offers.MockResponseUtil;
import com.wp.offers.data.Offer;
import com.wp.offers.repository.OfferRepository;
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
	
	@Test
	public void testFetchAllOffer() throws IOException, URISyntaxException {
		Iterable<Offer> expectedOfferIterables = mockUtil.getMockOfferList();
		
		List<Offer> list = (List<Offer>) expectedOfferIterables;
		
		Mockito
		.doReturn(expectedOfferIterables)
		.when(getOffersService.offerRepo).findAll();
		
		List<Offer> offers = getOffersService.fetchAllOffers();
		
		Assertions.assertEquals(list, offers);
	}
	
	@Test
	public void testExecuteImpl_fetchAll() throws Exception {
		getOffersService = Mockito.spy(GetOffersService.class);
		List<Offer> expectedOfferList = mockUtil.getMockOfferList();
		Mockito.doReturn(expectedOfferList).when(getOffersService).fetchAllOffers();
		getOffersService.executeImpl();
		Assertions.assertEquals(expectedOfferList.size(), getOffersService.getResponse().size());
		Assertions.assertEquals(expectedOfferList, getOffersService.getResponse());
	}
	
	
	@Test
	public void testExecuteImpl_fetchById() throws Exception {
		getOffersService = Mockito.spy(GetOffersService.class);
		Offer expectedOffer = mockUtil.getMockOffer();
		Mockito.doReturn(expectedOffer).when(getOffersService).fetchOfferById();
		getOffersService.setOfferId(1l);
		getOffersService.executeImpl();
		Assertions.assertEquals(1, getOffersService.getResponse().size());
		Assertions.assertEquals(expectedOffer, getOffersService.getResponse().get(0));
	}
	
}
