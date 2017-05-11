package csc330sms.exchange;
import java.io.IOException;
import java.math.*;

import csc330sms.exchange.MarkitAPI.CompanyInfo;
import csc330sms.exchange.MarkitAPI.StockNotFound;
import java.util.*;
import csc330sms.security.*;

public class StockExchange {
	private OrderQueue pendingBuyOrders;
	private OrderQueue pendingSellOrders;
	private ArrayList<Order> completedOrders;
	private MarkitAPI api;
	
	public StockExchange() {
		api = new MarkitAPI();
		completedOrders = new ArrayList<Order>();
	}
	
	/**
	 * Creates an order and processes it immediately if Order.Type is BUY_MARKET or SELL_MARKET.
	 * @param symbol
	 * @param quantity
	 * @param price
	 * @param orderType
	 * @param duration
	 * @return
	 * @throws StockNotFound
	 */
	public Order createOrder(String symbol, int quantity, BigDecimal price, Order.Type orderType, Order.Duration duration) throws StockNotFound {
		Order order = new Order(symbol, quantity, price, orderType, duration);
		
		processOrder(order);

		return order;
	}
	
	/**
	 * Processes an order. Returns true if the order is completed immediately.
	 * @param order
	 * @return
	 */
	private boolean processOrder(Order order) throws StockNotFound {
		if (order.isMarketOrder()) {
			StockQuote quote = api.getStockQuote(order.getSymbol());
			// Create underlying stock
			Stock s = new Stock(quote.symbol, quote.lastPrice, order.getQuantity(), quote.name);
			order.setSecurity(s);
			completedOrders.add(order);
			return true;
		}
		// TODO Process non-market type orders
		
		return false;
	}
	
	public BigDecimal getLastPrice(String symbol) throws MarkitAPI.StockNotFound { 
		StockQuote quote = api.getStockQuote(symbol);
		return quote.lastPrice;
	}
	
	public StockQuote getStockQuote(String symbol) throws MarkitAPI.StockNotFound {
		StockQuote quote = api.getStockQuote(symbol);
		return quote;
	}
	
	public ArrayList<CompanyInfo> getCompanyInfo(String company) {
		MarkitAPI api = new MarkitAPI();
		ArrayList<CompanyInfo> results = new ArrayList<CompanyInfo>();
		try {
			results = api.getCompanyInfo(company);
		} catch (IOException e) {
			// TODO Handle this
			e.printStackTrace();
		}
		return results;
	}
}
