package com.wp.offers.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wp.offers.MockResponseUtil;
import com.wp.offers.data.Offer;
import com.wp.offers.service.CreateOfferService;
import com.wp.offers.service.ExpireOfferService;
import com.wp.offers.service.GetOffersService;

public class OfferControllerTest {
	private OfferController restController;
	private MockResponseUtil mockUtil = new MockResponseUtil();
	
	@BeforeEach
	public void setUp() {
		restController = new OfferController();
		restController.createOfferService = Mockito.mock(CreateOfferService.class);
		restController.getOffersService = Mockito.mock(GetOffersService.class);
		restController.expireOffersService = Mockito.mock(ExpireOfferService.class);
	}
	
	@Test
	public void testCreateOffer_success() throws Exception {
		Offer offer = mockUtil.getMockOffer();
		Mockito.doReturn(offer).when(restController.createOfferService).getResponse();
		Mockito.doNothing().when(restController.createOfferService).execute();
		
		Offer requestBody = new Offer();
		requestBody.setName("Product1");
		requestBody.setDescription("Product description");
		requestBody.setAmount(20.50);
		requestBody.setExpiryDate(mockUtil.stringToDate("2020-12-31 23:59:59", "yyyy-MM-dd hh:mm:ss"));
		requestBody.setCreatedOn(new Date());
		ResponseEntity<Offer> response = restController.createOffer(requestBody);
		Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertNotNull(response.getBody().getId());
	}
	
	@Test
	public void testGetAllOffers_success() throws Exception {
		List<Offer> offers = mockUtil.getMockOfferList();
		Mockito.doReturn(offers).when(restController.getOffersService).getResponse();
		Mockito.doNothing().when(restController.getOffersService).execute();
		ResponseEntity<List<Offer>> response = restController.getOffers(null);
		Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertNotNull(response.getBody().size() > 0);
	}
	
	@Test
	public void expireOffer_success() throws Exception {
		Offer offer = mockUtil.getMockOffer();
		Mockito.doReturn(offer).when(restController.expireOffersService).getResponse();
		Mockito.doNothing().when(restController.expireOffersService).execute();
		
		ResponseEntity<Offer> response = restController.expireOffer(null);
		Assertions.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assertions.assertNotNull(response.getBody());
	}
}
