package csc330sms.broker;
import java.math.*;

import csc330sms.exchange.*;
import csc330sms.security.*;
import java.util.*;

/**
 * 
 * @author m
 *
 * The StockBrokerAccount class is the point of contact for buying, selling, and
 * viewing stocks. Its purpose is to serve as a public interface for the stock
 * market. Just like in real life, StockBrokerAccount the middle man between 
 * the investor and the stock exchange.
 * 
 * This class is interface agnostic. In other words, it will not be responsible for
 * displaying results to the user. That is the responsibility of the ApplicationLoader
 * class.
 * 
 * Future consideration:
 * Serialization, additional constructors
 * 
 * TODO Commission rates
 */
public class StockBrokerAccount {
	/** Cash balance of account holder */
	private BigDecimal accountBalance;
	private final double COMMISSION_FEE = 0.05;
	private Portfolio portfolio;
	private ArrayList<Order> pendingOrders;
	private ArrayList<Order> orderHistory;
	private StockExchange exchange;
	
	public StockBrokerAccount(BigDecimal balance) {
		accountBalance = balance;
		portfolio = new Portfolio();
		exchange = new StockExchange();
	}
	
	/**
	 * Creates a security order that is submitted to the stock exchange.
	 * @param symbol
	 * @param quantity
	 * @param price
	 * @param transactionType
	 * @param duration
	 * @return An Order.Status enumerator value.
	 */
	public Order createStockOrder(String symbol, int quantity, BigDecimal pricePerShare, Order.Type orderType, Order.Duration duration) 
			throws MarkitAPI.StockNotFound, InsufficientFunds {
		// Cost of the order -- will be subtracted from the account balance if order is completed
		BigDecimal cost = null;
		// Step 1: Verify that the account has sufficient funds to carry out BUY order
		if (orderType == Order.Type.BUY_MARKET)
		{
			BigDecimal price = exchange.getLastPrice(symbol);
			
			// Calculate the total share cost
			cost = price.multiply(new BigDecimal(quantity));
			
			// Calculate commission
			cost = cost.multiply(new BigDecimal(COMMISSION_FEE)).add(cost);
			
			if (cost.compareTo(accountBalance) > 0) {
				throw new InsufficientFunds();
			}
		}
		
		// TODO Check account balance for other types of orders
		
		Order order = exchange.createOrder(symbol, quantity, pricePerShare, orderType, duration);
		
		if (order.isComplete()) {
			Stock s = (Stock)order.getSecurity();
			portfolio.openStockPosition(s);
			accountBalance = accountBalance.subtract(cost);
		}
		return order;
	}
	
	public int destroyOrder(int orderID) {
		return 0;
	}
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	/**
	 * 
	 * @return A list of pending orders
	 */
	public ArrayList<Order> getPendingTransactions() {
		return pendingOrders;
	}
	
	/**
	 * 
	 * @param symbol The stock symbol
	 * @return a StockQuote object
	 */
	public StockQuote quoteStock(String symbol) throws MarkitAPI.StockNotFound {
		return exchange.getStockQuote(symbol);
	}
	
	public Portfolio getPortfolio() {
		return portfolio;
	}
	
	public BigDecimal getNetGainLoss() {
		// TODO
		return new BigDecimal(0);
	}
	
	public ArrayList<Order> getOrderHistory() {
		return orderHistory;
	}
	
	/**
	 * 
	 * @return The sum of the cash value of all positions.
	 */
	public BigDecimal getEquityValue() {
		// TODO
		return portfolio.getEquityValue();
	}
	
	/**
	 * 
	 * @return The sum of cash balance and equity value.
	 */
	public BigDecimal getAccountValue() {
		// TODO
		return new BigDecimal(0);
	}
	
	public class InsufficientFunds extends Exception {
		public InsufficientFunds() {
			super("Your account balance does not have sufficient funds to carry out this transaction.");
		}
	}
}