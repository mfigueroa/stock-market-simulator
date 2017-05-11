package csc330sms;

import java.util.*;
import csc330sms.CommandFramework;
import csc330sms.broker.*;
import csc330sms.broker.Portfolio.StockPositionNotFound;
import csc330sms.broker.StockBrokerAccount.InsufficientFunds;
import csc330sms.exchange.Order;
import csc330sms.exchange.MarkitAPI.StockNotFound;

import java.math.*;
import java.text.NumberFormat;

public class BuyCommand extends CommandFramework {
	
	public BuyCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("symbol", "The stock ticker.", ValidationType.STRING);
		this.addPositionalArgument("quantity", "The amount of shares.", ValidationType.INT);
		// TODO Add limit and stop orders
		//this.addPositionalArgument("order type", "The type of order (market, limit, or stop)");
	}
	
	public boolean run(String arguments) throws InvalidArgument {
		// Return control of the program to ApplicationLoader
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String symbol = (String)args.get(0).getArgument();
		int quantity = (int)args.get(1).getArgument();
		//String orderType = args.get(2).getArgument();
		
		// TODO Parse order type
		
		try {
			Order result = sba.createStockOrder(symbol, quantity, new BigDecimal("0"), Order.Type.BUY_MARKET, Order.Duration.DAY_ORDER);
			if (result.isComplete()) {
				System.out.println("Congratulations. Your order has been processed successfully.");
				System.out.println("Your new account equity value: " + NumberFormat.getCurrencyInstance().format(sba.getEquityValue()));
				System.out.println("Your new cash balance is: " + NumberFormat.getCurrencyInstance().format(sba.getAccountBalance()));
				
				System.out.println("\nORDER SUMMARY\n");
				System.out.printf("%-10s: %-5d\n", "SHARES", result.getQuantity());
				System.out.printf("%-10s: %-5s\n", "EQUITY", NumberFormat.getCurrencyInstance().format(result.getSecurity().getValue()));
			}
			// TODO Display other outcomes
		} catch (InsufficientFunds e) {
			System.out.println("You have insufficient funds to carry out this order.");
		} catch (StockNotFound e) {
			System.out.println("The stock symbol that you provided could not be found.");
		} catch (StockPositionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
