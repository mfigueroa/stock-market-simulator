package csc330sms.broker;

import csc330sms.security.Security;

import java.math.*;

public abstract class Position {
	protected Security security;
	
	public BigDecimal getValue() {
		return security.getValue();
	}
	
	public String getCompany() {
		return security.getCompany();
	}
	
	public String getSymbol() {
		return security.getSymbol();
	}
	
	public int getQuantity() {
		return security.getQuantity();
	}
}
