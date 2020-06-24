package com.wp.offers.repository;

import org.springframework.data.repository.CrudRepository;

import com.wp.offers.data.Offer;

public interface OfferRepository extends CrudRepository<Offer, Long> {}
