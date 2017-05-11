package csc330sms.exchange;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

import javax.swing.text.DateFormatter;

import java.math.*;
import csc330sms.exchange.StockQuote;

// JSON Simple
import org.json.simple.*;
import org.json.simple.parser.*;


public class MarkitAPI {
	
	static final String URL_LOOKUP = "http://dev.markitondemand.com/MODApis/Api/v2/Lookup/json?input={SEARCHTERM}";
	static final String URL_QUOTE = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol={SYMBOL}";
	static final String URL_INTERDATA = "http://dev.markitondemand.com/MODApis/Api/v2/InteractiveChart/json?parameters={JSON_STRING}";
	
	/**
	 * Searches for company information based on its name or symbol.
	 * @param searchTerm
	 * @return An array of CompanyInfo objects.
	 * @throws IOException
	 */
	public ArrayList<CompanyInfo> getCompanyInfo(String searchTerm) throws IOException {
		ArrayList<CompanyInfo> companies = new ArrayList<CompanyInfo>();
		
		String url = new String(URL_LOOKUP);
		url = url.replace("{SEARCHTERM}", searchTerm);
		JSONArray resultSet = (JSONArray)getJSON(url);
		
		Object[] arrayResult = resultSet.toArray();
		
		// It may return more than one company, so loop through JSON array
		for (int i = 0; i < arrayResult.length; i++) {
			JSONObject companyResults = (JSONObject)resultSet.get(i);
			
			String symbol = companyResults.get("Symbol").toString();
			String name = companyResults.get("Name").toString();
			String exchange = companyResults.get("Exchange").toString();
			
			companies.add(new CompanyInfo(name, symbol, exchange));
		}
		
		return companies;
	}
	
	/**
	 * Retrieves the stock quote based on the company symbol.
	 * @param symbol
	 * @return A StockQuote object
	 * @throws StockNotFound
	 */
	public StockQuote getStockQuote(String symbol) throws StockNotFound {
		StockQuote quote = new StockQuote();
		
		JSONObject json = (JSONObject)getJSON(URL_QUOTE.replace("{SYMBOL}", symbol));
		
		Object name = json.get("Name");
		if (name == null)
			throw new StockNotFound();
		
		quote.name = name.toString();
		
		quote.symbol = json.get("Symbol").toString();
		quote.lastPrice = new BigDecimal(json.get("LastPrice").toString());
		quote.change = new BigDecimal(json.get("Change").toString());
		quote.changePercent = Float.parseFloat(json.get("ChangePercent").toString());
		SimpleDateFormat fmt = new SimpleDateFormat();
		try {
			quote.timestamp = fmt.parse(json.get("Timestamp").toString());
		} catch (java.text.ParseException e) {
			quote.timestamp = new Date();
		} catch (java.lang.NullPointerException e) {
			quote.timestamp = new Date();
		}
		quote.marketCap = new BigDecimal(json.get("MarketCap").toString());
		quote.volume = new BigDecimal(json.get("Volume").toString());
		quote.changeYTD = Float.parseFloat(json.get("ChangeYTD").toString());
		quote.changePercentYTD = Float.parseFloat(json.get("ChangePercentYTD").toString());
		quote.high = new BigDecimal(json.get("High").toString());
		quote.low = new BigDecimal(json.get("Low").toString());
		quote.open = new BigDecimal(json.get("Open").toString());
		
		return quote;
	}
	
	/**
	 * Fetches the price, volume, and HLOC (high/low/open/close) for the given past days.
	 * @param symbol The stock symbol
	 * @param days The number of days in the past to fetch
	 * @return
	 */
	public Object getHistoricalData(String symbol, int days) {
		JSONObject obj = new JSONObject();
		obj.put("Normalized", "false");
		obj.put("NumberOfDays", days);
		obj.put("DataPeriod", "Day");
		
		// Open/close/high/low
		JSONObject elem = new JSONObject();
		elem.put("Symbol", symbol);
		elem.put("Type", "price");
		JSONArray params = new JSONArray();
		params.add("ohlc");
		elem.put("Params", params);
		
		// Volume
		JSONObject elem2 = new JSONObject();
		elem2.put("Symbol", symbol);
		elem2.put("Type", "volume");
		JSONArray params2 = new JSONArray();
		params2.add("");
		elem2.put("Params", params);
		
		JSONArray list = new JSONArray();
		list.add(elem);
		list.add(elem2);
		
		obj.put("Elements", list);
		// TODO Throw exception when stock is not found
		String url = URL_INTERDATA.replace("{JSON_STRING}", obj.toJSONString());
		JSONObject resp = (JSONObject)getJSON(url);
		
		return resp;
	}
	
	/**
	 * Returns a JSON object from the requested URL.
	 * @param requestURL A string with the URL
	 * @return A JSON Object. This could either be a JSONArray or JSONObject,
	 * 	depending on the expected output of the Markit API.
	 */
	private Object getJSON(String requestURL) {
		URL url;
		Object obj = null;
		
		try {
			url = new URL(requestURL);
			InputStreamReader isr = new InputStreamReader(url.openStream());
			BufferedReader inStream = new BufferedReader(isr);
			
			StringBuffer buffer = new StringBuffer();
			String inputLine;
			
			while ((inputLine = inStream.readLine()) != null) {
				buffer.append(inputLine);
			}
			
			JSONParser parser = new JSONParser();
			
			try {
				obj = parser.parse(buffer.toString());
			} catch (ParseException e) {
				obj = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public class CompanyInfo {
		public String name;
		public String symbol;
		public String exchange;
		
		CompanyInfo(String name, String symbol, String exchange) {
			this.name = name;
			this.symbol = symbol;
			this.exchange = exchange;
		}
	}
	
	public class StockNotFound extends Exception {
		public StockNotFound() {
			super("Error: Could not find stock based on symbol.");
		}
	}
}
