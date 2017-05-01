package csc330sms.test;

import static org.junit.Assert.*;
import csc330sms.broker.*;
import csc330sms.exchange.*;
import csc330sms.security.*;
import java.math.*;

import org.junit.Test;

public class TestPortfolio {

	@Test
	public void testAddToPortfolio() {
		Portfolio portfolio = new Portfolio();
		Stock s1 = new Stock("AAPL", new BigDecimal(100), 20, "Apple Inc");
		Stock s2 = new Stock("AAPL", new BigDecimal(150), 50, "Apple Inc");
		
		portfolio.openStockPosition(s1);
		
		assert(portfolio.getStockPosition("AAPL").getValue().equals(new BigDecimal("2000")));
		
		portfolio.openStockPosition(s2);

		assert(portfolio.getStockPosition("AAPL").getValue().equals(new BigDecimal("9499.70")));

	}

}
