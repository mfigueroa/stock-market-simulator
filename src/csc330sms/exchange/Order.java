package csc330sms.exchange;
import java.math.*;

public class Order {
	public enum Status {
		COMPLETE, PENDING, INSUFFICIENT_FUNDS, CANCELED
	}
	
	public enum Duration {
		DAY_ORDER, GTC
	}
	
	public enum Type {
		MARKET, LIMIT, STOP
	}
	
	private int status;
	private String symbol;
	private int quantity;
	private BigDecimal price;
	private int orderType;
	private int duration;
	
	Order(String symbol, int quantity, BigDecimal price, int orderType, int duration)
	{
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		this.orderType = orderType;
		this.duration = duration;
	}
	
	public int getStatus() {
		return status;
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
	
	public int getOrderType() {
		return orderType;
	}
	
	public int getDuration() {
		return duration;
	}
	
}
