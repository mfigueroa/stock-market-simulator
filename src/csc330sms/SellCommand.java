package csc330sms;

import java.util.ArrayList;

import csc330sms.CommandFramework.CommandArgument;
import csc330sms.broker.Portfolio.StockPositionNotFound;
import csc330sms.broker.StockBrokerAccount;
import csc330sms.broker.StockBrokerAccount.InsufficientFunds;
import csc330sms.exchange.*;
import csc330sms.exchange.MarkitAPI.StockNotFound;

import java.math.*;
import java.text.NumberFormat;

public class SellCommand extends CommandFramework {

	public SellCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("symbol", "The stock ticker.", ValidationType.STRING);
		this.addPositionalArgument("quantity", "The amount of shares.", ValidationType.INT);
		// TODO Limit and stop orders
	}
	
	public boolean run(String arguments) throws InvalidArgument {
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String symbol = (String)args.get(0).getArgument();
		int quantity = (int)args.get(1).getArgument();
		
		try {
			Order result = sba.createStockOrder(symbol, quantity, new BigDecimal("0"), Order.Type.SELL_MARKET, Order.Duration.DAY_ORDER);
			
			System.out.printf("You have successfully sold %d shares of %s for a total value of %s.\n", 
					quantity, result.getSymbol(), NumberFormat.getCurrencyInstance().format(result.getSecurity().getValue()));
			
		} catch (InsufficientFunds e) {
			System.out.println("You lack insufficient funds to carry out this order.");
		} catch (StockNotFound e) {
			System.out.println("The stock ticker you provided could not be found.");
		} catch (StockPositionNotFound e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
