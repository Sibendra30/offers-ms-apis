package com.wp.offers.service;

public abstract class BaseService {
	
	protected void validate() throws Exception{}
	
	public void execute() throws Exception {
		this.validate();
		this.executeImpl();
	}
	
	protected abstract void executeImpl() throws Exception ;
}
