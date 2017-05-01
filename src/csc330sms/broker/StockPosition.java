package csc330sms.broker;

import java.math.BigDecimal;
import java.math.RoundingMode;

import csc330sms.security.Stock;

public class StockPosition extends Position {

	public StockPosition(Stock s) {
		this.security = s;
	}
	
	/**
	 * Adds a given position to the current position.
	 * @param p
	 */
	public void addTo(Stock s) {
		int newQuantity = s.getQuantity() + this.getQuantity();
		BigDecimal newPrice = s.getValue().add(this.getValue()).divide(new BigDecimal(newQuantity), 2, RoundingMode.HALF_EVEN);
		this.security = new Stock(security.getSymbol(), newPrice, newQuantity, security.getCompany());
	}

}
