package csc330sms.broker;

import java.util.ArrayList;

import csc330sms.security.Security;
import csc330sms.security.Stock;

import java.math.BigDecimal;
import java.util.*;

// TODO
public class Portfolio {
	private ArrayList<StockPosition> stockPositions;
	
	public Portfolio() {
		stockPositions = new ArrayList<StockPosition>();
	}
	
	/**
	 * Opens a position for a given stock object.
	 * If a position for a stock symbol already exists, merges
	 * both positions and averages prices.
	 * @param s
	 * @return
	 */
	public boolean openStockPosition(Stock s) {
		StockPosition p = getStockPosition(s.getSymbol());
		
		if (p != null) {
			p.addTo(s);
			return true;
		} else {
			// Create a new position
			p = new StockPosition(s);
			stockPositions.add(p);
		}
		return true;
	}
	
	/**
	 * Returns a StockPosition.
	 * @param s
	 * @return Returns a StockPosition if found, and null otherwise.
	 */
	public StockPosition getStockPosition(String symbol) {
		for (int i = 0; i < stockPositions.size(); i++) {
			if (stockPositions.get(i).security.getSymbol().equals(symbol)) {
				return stockPositions.get(i);
			}
		}
		return null;
	}
	
	public BigDecimal getEquityValue() {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < stockPositions.size(); i ++) {
			sum = sum.add(stockPositions.get(i).getValue());
		}
		return sum;
	}
}
