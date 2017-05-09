package csc330sms.exchange;
import java.math.*;
import java.util.Date;

import csc330sms.security.*;

/**
 * The Order class encapsulates a security during a transaction. Once a transaction
 * has been completed, the underlying security can be passed off to another class
 * such as a Position.
 * @author m
 *
 */
public class Order {
	public enum Status {
		COMPLETE, PENDING, INSUFFICIENT_FUNDS, CANCELED
	}
	
	public enum Duration {
		DAY_ORDER, GTC
	}
	
	public enum Type {
		BUY_MARKET, BUY_LIMIT, BUY_STOP, SELL_MARKET, SELL_LIMIT, SELL_STOP
	}
	
	private Status status;
	private String symbol;
	private int quantity;
	// Price per security. This value cannot be changed after Order is created.
	// The actual purchase price is reflected inside the security.
	private final BigDecimal price;
	private Date timestamp;
	private Order.Type type;
	private Order.Duration duration;
	private Security security;
	
	Order(String symbol, int quantity, BigDecimal price, Order.Type orderType, Order.Duration duration)
	{
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		this.type = orderType;
		this.duration = duration;
		this.timestamp = new Date();
	}
	
	/**
	 * Returns true if the Order has been carried out successfully by the stock exchange.
	 * @return
	 */
	public boolean isComplete() {
		if (status == Status.COMPLETE)
			return true;
		return false;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getQuantity() {
		// The quantity of securities
		return quantity;
	}
	
	/**
	 * The price that the order will be executed on.
	 * @return
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	public Type getOrderType() {
		return type;
	}
	
	public Duration getDuration() {
		return duration;
	}
	
	/**
	 * Assigns a security to this order. Once a security has been assigned, it is considered complete
	 * @param s
	 */
	public void setSecurity(Security s) {
		security = s;
		this.status = Status.COMPLETE;
	}
	
	/**
	 * The underlying security that the order was executed on. This security reflects
	 * the final value of the order.
	 * @return
	 */
	public Security getSecurity() {
		return security;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @return Returns true if Order is a type of market order.
	 */
	public boolean isMarketOrder() {
		if (type == Order.Type.BUY_MARKET || type == Order.Type.SELL_MARKET) {
			return true;
		}
		return false;
	}
	
}
