package com.wp.offers.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.wp.offers.data.Offer;

@Component
public class OffersUtil {

	public boolean checkIfOfferExpired(Date expiryDate) {
		return expiryDate.compareTo(new Date()) < 0;
	}
}
