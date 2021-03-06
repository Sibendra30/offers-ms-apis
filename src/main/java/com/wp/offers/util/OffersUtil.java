package com.wp.offers.util;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.wp.offers.data.Offer;

@Component
public class OffersUtil {

	public boolean checkIfOfferExpired(LocalDateTime expiryDate) {
		return expiryDate.compareTo(LocalDateTime.now()) < 0;
	}
}
