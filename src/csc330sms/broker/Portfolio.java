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
		StockPosition p;
		
		try {
			// Add to existing position
			p = getStockPosition(s.getSymbol());
			p.addTo(s);
		} catch (StockPositionNotFound e) {
			// Create a new position
			p = new StockPosition(s);
			stockPositions.add(p);
		}
		return true;
	}
	
	/**
	 * Reduces a given stock position. If quantity is equal or greater to the maximum number of
	 * shares, the stock position is closed.
	 * 
	 */
	public boolean reduceStockPosition(Stock s) throws StockPositionNotFound {
		StockPosition p = getStockPosition(s.getSymbol());
		if (p != null) {
			if (s.getQuantity() >= p.getQuantity()) {
				closeStockPosition(s);
			} else {
				p.reduce(s.getQuantity());
			}
		}
		return false;
	}
	
	/**
	 * Closes a given stock position.
	 * @param s A given stock
	 * @return Returns true if the position was removed. False otherwise.
	 */
	public boolean closeStockPosition(Stock s) {
		for (int i = 0; i < stockPositions.size(); i ++) {
			if (stockPositions.get(i).getSymbol().equalsIgnoreCase(s.getSymbol())) {
				stockPositions.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds a StockPosition in the portfolio for a given symbol.
	 * @param s
	 * @return Returns a StockPosition if found, and null otherwise.
	 */
	public StockPosition getStockPosition(String symbol) throws StockPositionNotFound {
		for (int i = 0; i < stockPositions.size(); i++) {
			if (stockPositions.get(i).security.getSymbol().equalsIgnoreCase(symbol)) {
				return stockPositions.get(i);
			}
		}
		throw new StockPositionNotFound();
	}
	
	public BigDecimal getEquityValue() {
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < stockPositions.size(); i ++) {
			sum = sum.add(stockPositions.get(i).getValue());
		}
		return sum;
	}
	
	public final ArrayList<StockPosition> getStockPositions() {
		return this.stockPositions;
	}
	
	public class StockPositionNotFound extends Exception {
		
	}
}
