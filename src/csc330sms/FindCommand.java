package csc330sms;

import java.util.ArrayList;

import csc330sms.broker.StockBrokerAccount;
import csc330sms.exchange.MarkitAPI.CompanyInfo;
import csc330sms.exchange.MarkitAPI.StockNotFound;

public class FindCommand extends CommandFramework {

	public FindCommand(StockBrokerAccount sba) {
		super(sba);
		this.addPositionalArgument("company", "The name of the company to search for.", ValidationType.STRING);
	}
	
	@Override
	public boolean run(String arguments) throws InvalidArgument {
		if (!super.run(arguments)) return false;
		
		ArrayList<CommandArgument> args = this.getPositionalArguments();
		String company = (String)args.get(0).getArgument();
		
		ArrayList<CompanyInfo> companies = sba.getCompanyInfo(company);
		
		if (companies.size() < 0) {
			System.out.println("No companies were found.");
		} else {
			System.out.printf("%-50s %-10s %-10s\n", "NAME", "SYMBOL", "EXCHANGE");
			
			for (int i = 0; i < companies.size(); i++) {
				System.out.printf("%-50s %-10s %-10s\n", companies.get(i).name, companies.get(i).symbol, companies.get(i).exchange);
			}
		}
		return true;
	}
}
