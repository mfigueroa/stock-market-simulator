package csc330sms.broker;
import java.math.*;
import csc330sms.exchange.*;
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
 */
public class StockBrokerAccount {
	private BigDecimal accountBalance;
	private final double TRANSACTION_FEE = 0.1;
	private Portfolio portfolio;
	private ArrayList<Order> pendingOrders;
	private ArrayList<Order> orderHistory;
	private StockExchange stockex;
	
	StockBrokerAccount(BigDecimal balance) {
		accountBalance = balance;
		portfolio = new Portfolio();
		stockex = new StockExchange();
	}
	
	/**
	 * Creates a security order that is submitted to the stock exchange.
	 * @param symbol
	 * @param quantity
	 * @param price
	 * @param transactionType
	 * @param duration
	 * @return An Order.Result enumerator value.
	 */
	int createOrder(String symbol, int quantity, BigDecimal price, int transactionType, int duration) {
		return 0;
	}
	
	int destroyOrder(int orderID) {
		return 0;
	}
	
	BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	/**
	 * 
	 * @return A list of pending orders
	 */
	ArrayList<Order> getPendingTransactions() {
		return pendingOrders;
	}
	
	/**
	 * 
	 * @param symbol The stock symbol
	 * @return a StockQuote object
	 */
	StockQuote quoteStock(String symbol) {
		// TODO
		return new StockQuote();
	}
	
	Portfolio getPortfolio() {
		return portfolio;
	}
	
	BigDecimal getNetGainLoss() {
		// TODO
		return new BigDecimal(0);
	}
	
	ArrayList<Order> getOrderHistory() {
		return orderHistory;
	}
	
	/**
	 * 
	 * @return The sum of the cash value of all positions.
	 */
	BigDecimal getEquityValue() {
		// TODO
		return new BigDecimal(0);
	}
	
	/**
	 * 
	 * @return The sum of cash balance and equity value.
	 */
	BigDecimal getAccountValue() {
		// TODO
		return new BigDecimal(0);
	}
}