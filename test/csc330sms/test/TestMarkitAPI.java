package csc330sms.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;
import csc330sms.exchange.MarkitAPI;
import csc330sms.exchange.MarkitAPI.CompanyInfo;
import java.io.*;

public class TestMarkitAPI {

	@Test
	public void test() throws IOException {
		MarkitAPI api = new MarkitAPI();
		ArrayList<CompanyInfo> results = api.getCompanyInfo("Netflix");
		assert(results.get(0).symbol.equals("NFLX"));
	}

}
