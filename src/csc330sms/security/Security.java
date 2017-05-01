package csc330sms.security;
import java.math.*;
/**
 * 
 * Security is the generalization of stocks, options, bonds, and any other stock market instrument.
 * It is a blueprint for how stocks (and other instruments) should be implemented.
 * 
 */
public abstract class Security {
	private BigDecimal price;
	private int quantity;
	private String company;
	private String symbol;
	
	public Security(String symbol, BigDecimal price, int quantity, String company) {
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;
		this.company = company;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getCompany() {
		return company;
	}
	
	public BigDecimal getValue() { 
		return price.multiply(new BigDecimal(quantity));
	}
}
