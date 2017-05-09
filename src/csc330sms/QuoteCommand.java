package csc330sms;

import java.text.NumberFormat;
import java.util.ArrayList;

import csc330sms.CommandFramework.CommandArgument;
import csc330sms.broker.StockBrokerAccount;
import csc330sms.exchange.*;

public class QuoteCommand extends CommandFramework {

	public QuoteCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("symbol", "Stock symbol", ValidationType.STRING);
	}
	
	public boolean run(String arguments) throws InvalidArgument {
		// Return control of the program to ApplicationLoader
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String symbol = args.get(0).getArgument().toString();
		
		try {
			StockQuote quote = sba.quoteStock(symbol);
			System.out.printf("%-10s: %-5s\n", "COMPANY", quote.name);
			System.out.printf("%-10s: %-5s\n", "SYMBOL", quote.symbol);
			System.out.printf("%-10s: %-5s\n", "LAST PRICE", NumberFormat.getCurrencyInstance().format(quote.lastPrice));
			System.out.printf("%-10s: %-5.2f%%\n", "CHANGE", quote.changePercent);
		} catch (MarkitAPI.StockNotFound e) {
			// TODO User-friendly output
			e.printStackTrace();
		}
		
		return true;
	}
}
