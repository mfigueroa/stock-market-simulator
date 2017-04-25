package csc330sms.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;
import csc330sms.exchange.MarkitAPI.*;
import java.io.*;
import csc330sms.exchange.*;

public class TestMarkitAPI {

	@Test
	public void testCompanyInfo() throws IOException {
		MarkitAPI api = new MarkitAPI();
		ArrayList<CompanyInfo> results = api.getCompanyInfo("Netflix");
		assert(results.get(0).symbol.equals("NFLX"));
	}
	
	@Test
	public void testStockQuote() throws IOException, StockNotFound {
		MarkitAPI api = new MarkitAPI();
		StockQuote quote = api.getStockQuote("AAPL");
		assert(quote.name.equals("Apple Inc"));
	}

}
