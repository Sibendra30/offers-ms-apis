package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;

import com.wp.offers.MockResponseUtil;
import com.wp.offers.data.Offer;
import com.wp.offers.repository.OfferRepository;

public class CreateOfferServiceTest {

	private CreateOfferService service;
	private MockResponseUtil mockUtil; 

	@BeforeEach
	public void setup() throws IOException, URISyntaxException {
		service = new CreateOfferService();
		service.offerRepo = Mockito.mock(OfferRepository.class);
		service.applicationProperties = Mockito.mock(Environment.class);
		mockUtil = new MockResponseUtil();
	}

	@Test
	public void testValidate_nullOffer() {
		Exception ex = Assertions.assertThrows(MissingServletRequestParameterException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testValidate_nullName() throws IOException, URISyntaxException {
		Offer offer = mockUtil.getMockOffer();
		offer.setName(null);
		service.setOffer(offer);
		Exception ex = Assertions.assertThrows(MissingServletRequestParameterException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testValidate_emptyDesc() throws IOException, URISyntaxException {
		Offer offer = mockUtil.getMockOffer();
		offer.setDescription("");
		service.setOffer(offer);
		Exception ex = Assertions.assertThrows(MissingServletRequestParameterException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testValidate_invalidAmount() throws IOException, URISyntaxException {
		Offer offer = mockUtil.getMockOffer();
		offer.setAmount(-1.5);
		service.setOffer(offer);
		Exception ex = Assertions.assertThrows(MissingServletRequestParameterException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testValidate_invalidExpiryDate() throws IOException, URISyntaxException, ParseException {
		Offer offer = mockUtil.getMockOffer();
		offer.setExpiryDate(LocalDateTime.of(2019, Month.AUGUST, 20, 23, 59, 59));
		service.setOffer(offer);
		Exception ex = Assertions.assertThrows(ServletRequestBindingException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testGetExpiryDate_nullInput() throws IOException, URISyntaxException, ParseException {
		Mockito.doReturn("3").when(service.applicationProperties).getProperty("default.expire.month");
		LocalDateTime dateTimeNow = LocalDateTime.now()
				.plusMonths(3)
				.withHour(23)
				.withMinute(59)
				.withSecond(59);
		
		LocalDateTime expiryDate = service.getExpiryDate(null);
		System.out.println(dateTimeNow);
		System.out.println(expiryDate);
		Assertions.assertTrue(expiryDate.compareTo(dateTimeNow) == 0);
	}
	
	@Test
	public void testGetExpiryDate_NonNullInput() throws IOException, URISyntaxException, ParseException {
		
		LocalDateTime localDateTimeNow = LocalDateTime.now()
				.withHour(23)
				.withMinute(59)
				.withSecond(59);
		
		LocalDateTime expiryDate = service.getExpiryDate(LocalDateTime.now());
		
		Assertions.assertTrue(expiryDate.compareTo(localDateTimeNow) == 0);
	}

	@Test
	public void testExecuteImpl() throws Exception {
		Offer offer = mockUtil.getMockOffer();

		Offer reqBody = offer.clone();
		reqBody.setId(null);
		reqBody.setCreatedOn(null);
		reqBody.setExpiryDate(null);
		
		//offer.setCreatedOn(new Date());

		Mockito.doReturn(offer).when(service.offerRepo).save(reqBody);
		Mockito.doReturn("3").when(service.applicationProperties).getProperty("default.expire.month");

		service.setOffer(reqBody);
		service.executeImpl();
		offer = service.getResponse();

		Assertions.assertNotNull(offer);
		Assertions.assertNotNull(offer.getId());
		Assertions.assertNotNull(service.getOfferReq());
		//Assertions.assertNotNull(service.getOfferReq().getCreatedOn());
		Assertions.assertNotNull(service.getOfferReq().getExpiryDate());
	}
}
