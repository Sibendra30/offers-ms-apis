package com.wp.offers.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;

import com.wp.offers.data.Offer;
import com.wp.offers.repository.OfferRepository;

@Service
@Scope("prototype")
public class CreateOfferService extends BaseService{

	@Autowired
	protected OfferRepository offerRepo;
	
	@Autowired
	protected Environment applicationProperties;

	private Offer offerReq;
	private Offer offerRes;


	@Override
	protected void validate() throws ServletRequestBindingException {
		if (offerReq == null 
				|| StringUtils.isEmpty(offerReq.getName()) 
				|| StringUtils.isEmpty(offerReq.getDescription())
				|| offerReq.getAmount() <= 0) {
			throw new MissingServletRequestParameterException("Mandatory parameter missing or empty", null);
		}

		if (offerReq.getExpiryDate() !=null && offerReq.getExpiryDate().compareTo(new Date()) <= 0) {
			throw new ServletRequestBindingException("Invalid expiry date");
		}
	}

	public void executeImpl() {
		offerReq.setCreatedOn(new Date());
		offerReq.setExpiryDate(
				this.getExpiryDate(offerReq.getExpiryDate()));
		offerRes = offerRepo.save(offerReq);
	}

	protected Date getExpiryDate(Date expiryDate) {
		Calendar calendarObj = Calendar.getInstance();
		if (expiryDate == null) {
			calendarObj.setTime(new Date());
			calendarObj.add(Calendar.MONTH, 
					Integer.parseInt(applicationProperties.getProperty("default.expire.month")));
		} else {
			calendarObj.setTime(expiryDate);
		}
		calendarObj.set(Calendar.HOUR, 23);
		calendarObj.set(Calendar.MINUTE, 59);
		calendarObj.set(Calendar.MINUTE, 59);
		calendarObj.set(Calendar.MILLISECOND, 999);
		return calendarObj.getTime();
	}

	public Offer getResponse() {
		return offerRes;
	}

	public Offer getOfferReq() {
		return offerReq;
	}

	public void setOfferReq(Offer offerReq) {
		this.offerReq = offerReq;
	}

	public Offer getOfferRes() {
		return offerRes;
	}

	public void setOfferRes(Offer offerRes) {
		this.offerRes = offerRes;
	}
	
	public void setOffer(Offer offer) {
		this.offerReq = offer;
	}

}
