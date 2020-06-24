package com.wp.offers.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.wp.offers.OfferRepository;
import com.wp.offers.data.Offer;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Offer offer = mockUtil.getMockOffer();
		offer.setExpiryDate(sdf.parse("2019-01-13"));
		service.setOffer(offer);
		Exception ex = Assertions.assertThrows(ServletRequestBindingException.class, () -> {
			service.validate();
		});
		
		Assertions.assertNotNull(ex.getMessage());
	}
	
	@Test
	public void testGetExpiryDate_nullInput() throws IOException, URISyntaxException, ParseException {
		Mockito.doReturn("3").when(service.applicationProperties).getProperty("default.expire.month");
		Calendar calObj = Calendar.getInstance();
		calObj.setTime(new Date());
		calObj.add(Calendar.MONTH, 3);
		calObj.set(Calendar.HOUR, 23);
		calObj.set(Calendar.MINUTE, 59);
		calObj.set(Calendar.MINUTE, 59);
		calObj.set(Calendar.MILLISECOND, 999);
		
		Date expiryDate = service.getExpiryDate(null);
		
		Assertions.assertTrue(expiryDate.getTime() == calObj.getTimeInMillis());
	}
	
	@Test
	public void testGetExpiryDate_NonNullInput() throws IOException, URISyntaxException, ParseException {
		Date input = new Date();
		
		Calendar calObj = Calendar.getInstance();
		calObj.setTime(input);
		calObj.set(Calendar.HOUR, 23);
		calObj.set(Calendar.MINUTE, 59);
		calObj.set(Calendar.MINUTE, 59);
		calObj.set(Calendar.MILLISECOND, 999);
		
		Date expiryDate = service.getExpiryDate(input);
		
		Assertions.assertTrue(expiryDate.getTime() == calObj.getTimeInMillis());
	}

	@Test
	public void testExecuteImpl() throws Exception {
		Offer offer = mockUtil.getMockOffer();

		Offer reqBody = offer.clone();
		reqBody.setId(null);
		reqBody.setCreatedOn(null);
		reqBody.setExpiryDate(null);
		
		offer.setCreatedOn(new Date());

		Mockito.doReturn(offer).when(service.offerRepo).save(reqBody);
		Mockito.doReturn("3").when(service.applicationProperties).getProperty("default.expire.month");

		service.setOffer(reqBody);
		service.executeImpl();
		offer = service.getResponse();

		Assertions.assertNotNull(offer);
		Assertions.assertNotNull(offer.getId());
		Assertions.assertNotNull(service.getOfferReq());
		Assertions.assertNotNull(service.getOfferReq().getCreatedOn());
		Assertions.assertNotNull(service.getOfferReq().getExpiryDate());
	}
}
