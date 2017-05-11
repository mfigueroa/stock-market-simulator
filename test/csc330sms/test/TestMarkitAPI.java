package csc330sms.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
	
	@Test
	public void testInteractiveChart() throws IOException {
		MarkitAPI api = new MarkitAPI();
		JSONObject obj = (JSONObject)api.getHistoricalData("AAPL", 5);
		JSONArray elems = (JSONArray)obj.get("Elements");
		JSONObject elems2 = (JSONObject)elems.get(0);
		assert(elems2.get("Type").toString().equals("price"));
	}

}
