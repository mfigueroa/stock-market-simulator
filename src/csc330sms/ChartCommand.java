package csc330sms;

import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import csc330sms.CommandFramework.CommandArgument;
import csc330sms.CommandFramework.InvalidArgument;
import csc330sms.CommandFramework.ValidationType;
import csc330sms.broker.StockBrokerAccount;
import csc330sms.exchange.MarkitAPI;

public class ChartCommand extends CommandFramework {

	public ChartCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("symbol", "The stock ticker.", ValidationType.STRING);
		this.addPositionalArgument("days", "The number of days to chart.", ValidationType.INT);
	}
	
	@Override
	public boolean run(String arguments) throws InvalidArgument {
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String symbol = (String)args.get(0).getArgument();
		int days = (int)args.get(1).getArgument();
		
		try {
			String company = sba.quoteStock(symbol).name;
			
			StockChart chart = new StockChart(company, symbol, days);
			chart.pack();
	        RefineryUtilities.centerFrameOnScreen(chart);
	        chart.setVisible(true);
		} catch (MarkitAPI.StockNotFound e) {
			System.out.println("The stock symbol was not found.");
		}
        
        return true;
	}
}
