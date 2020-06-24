package com.wp.offers.data;

public class OfferPrice {

	private double amount;
	private double taxIncludedAmount;
	private double taxRate;
	private String currency;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTaxIncludedAmount() {
		return taxIncludedAmount;
	}
	public void setTaxIncludedAmount(double taxIncludedAmount) {
		this.taxIncludedAmount = taxIncludedAmount;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		temp = Double.doubleToLongBits(taxIncludedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(taxRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfferPrice other = (OfferPrice) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (Double.doubleToLongBits(taxIncludedAmount) != Double.doubleToLongBits(other.taxIncludedAmount))
			return false;
		if (Double.doubleToLongBits(taxRate) != Double.doubleToLongBits(other.taxRate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OfferPrice [amount=" + amount + ", taxIncludedAmount=" + taxIncludedAmount + ", taxRate=" + taxRate
				+ ", currency=" + currency + "]";
	}
	
}
