package csc330sms.security;
import java.math.*;
/**
 * 
 * Security is the generalization of stocks, options, bonds, and any other stock market instrument.
 * It is a blueprint for how stocks (and other instruments) should be implemented.
 * 
 * IMPORTANT: This is an immutable class.
 * 
 */
public abstract class Security {
	// The price of the individual share, option, etc.
	private BigDecimal price;
	// The number of securities
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
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public String getCompany() {
		return company;
	}
	
	public BigDecimal getValue() { 
		return price.multiply(new BigDecimal(quantity));
	}
}
