package csc330sms;

import java.util.*;
import csc330sms.CommandFramework;
import csc330sms.broker.*;
import csc330sms.broker.StockBrokerAccount.InsufficientFunds;
import csc330sms.exchange.Order;
import csc330sms.exchange.MarkitAPI.StockNotFound;

import java.math.*;

public class BuyCommand extends CommandFramework {
	
	public BuyCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("symbol", "The stock ticker.");
		this.addPositionalArgument("quantity", "The amount of shares.");
		this.addPositionalArgument("order type", "The type of order (market, limit, or stop)");
	}
	
	public boolean run(String arguments) {
		// Return control of the program to ApplicationLoader
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String symbol = args.get(0).getArgument();
		int quantity = Integer.parseInt(args.get(1).getArgument());
		String orderType = args.get(2).getArgument();
		
		// TODO Parse order type
		
		try {
			Order.Status result = sba.createStockOrder(symbol, quantity, new BigDecimal("0"), Order.Type.BUY_MARKET, Order.Duration.DAY_ORDER);
			if (result == Order.Status.COMPLETE) {
				// TODO Display portfolio summary
				System.out.println("Congratulations. Your order has been processed successfully.");
				System.out.println("Your new account equity value: "+ sba.getEquityValue());
			}
			// TODO Display other outcomes
		} catch (InsufficientFunds e) {
			// TODO User friendly output
			e.printStackTrace();
		} catch (StockNotFound e) {
			// TODO User friendly output
			e.printStackTrace();
		}
		return true;
	}
}
