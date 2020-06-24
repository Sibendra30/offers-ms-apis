package com.wp.offers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;
import com.wp.offers.data.Offer;

public class MockResponseUtil {

	public Offer getMockOffer() throws IOException, URISyntaxException {
		Offer offer = new Offer();
		offer.setId(1l);
		offer.setName("Redmi Note8 Pro");
		offer.setDescription("Smart phone");
		offer.setAmount(220.50);
		offer.setCreatedOn(LocalDateTime.now());
		offer.setExpiryDate(LocalDateTime.now().plusMonths(3));
		offer.setExpired(Boolean.FALSE);
		return offer;
	}
	
	public List<Offer> getMockOfferList() throws IOException, URISyntaxException {
		List<Offer> offerList = new ArrayList<>();
		Offer offer = new Offer();
		offer.setId(1l);
		offer.setName("Redmi Note8 Pro");
		offer.setDescription("Smart phone");
		offer.setAmount(220.50);
		offer.setCreatedOn(LocalDateTime.now());
		offer.setExpiryDate(LocalDateTime.now().plusMonths(3));
		offer.setExpired(Boolean.FALSE);
		
		offerList.add(offer);
		
		offer = offer.clone();
		offer.setName("Flying Disc");
		offer.setDescription("12cm diameter flying disc");
		offer.setAmount(2.50);
		
		offerList.add(offer);
		return offerList;
	}
	
}
