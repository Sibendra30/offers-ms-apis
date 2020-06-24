package com.wp.offers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.wp.offers.data.Offer;

public class MockResponseUtil {

	public Offer getMockOffer() throws IOException, URISyntaxException {
		ClassLoader loader = ClassLoader.getSystemClassLoader();

		String json = Files.lines(Paths.get(loader.getResource("offer.json").toURI()))
		                   .parallel()
		                   .collect(Collectors.joining());
		Gson gson = new Gson();
		return gson.fromJson(json, Offer.class);
	}
	
	public List<Offer> getMockOfferList() throws IOException, URISyntaxException {
		ClassLoader loader = ClassLoader.getSystemClassLoader();

		String json = Files.lines(Paths.get(loader.getResource("offers.json").toURI()))
		                   .parallel()
		                   .collect(Collectors.joining());
		Gson gson = new Gson();
		return gson.fromJson(json, List.class);
	}
}
