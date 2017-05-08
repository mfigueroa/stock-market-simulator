package csc330sms;

import java.util.ArrayList;
import java.math.*;
import java.text.NumberFormat;

import csc330sms.CommandFramework.CommandArgument;
import csc330sms.broker.StockBrokerAccount;

public class AccountCommand extends CommandFramework {

	public AccountCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("request", "Stock, options, cash, or summary.");
	}
	
	private void printAccountBalance() {
		String balance = NumberFormat.getCurrencyInstance().format(sba.getAccountBalance());
		System.out.printf("You have a total cash balance of %s\n", balance);
	}
	
	private void printEquityBalance() {
		String balance = NumberFormat.getCurrencyInstance().format(sba.getEquityValue());
		System.out.printf("You have a total equity balance of %s\n", balance);
	}
	
	public boolean run(String arguments) throws InvalidArgument {
		// Return control of the program to ApplicationLoader
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String request = args.get(0).getArgument().toString();
		
		if (request.contains("cash")) {
			printAccountBalance();
		}
		
		if (request.contains("summary")) {
			printAccountBalance();
			printEquityBalance();
			
		}
		return true;
	}
}
