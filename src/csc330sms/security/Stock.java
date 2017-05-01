package csc330sms.security;

import java.math.BigDecimal;

import csc330sms.security.Security;

public class Stock extends Security {

	public Stock(String symbol, BigDecimal price, int quantity, String company) {
		super(symbol, price, quantity, company);
	}

}
