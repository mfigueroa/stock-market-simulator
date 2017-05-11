package csc330sms.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import csc330sms.ApplicationLoader;
import csc330sms.CommandFramework;
import csc330sms.ApplicationLoader.CommandNotFound;

public class TestCommands {

	@Test
	public void testBuy() {
		ApplicationLoader app = new ApplicationLoader();
		
		try {
			ArrayList<Object> command = app.getCommandTuple("buy");
			
			CommandFramework cmd = (CommandFramework)command.get(1);
			
			cmd.run("aapl 10");
		} catch (CommandFramework.InvalidArgument e) {
			System.out.println(e.getMessage());
		} catch (CommandNotFound e) {
			System.out.println("Command not found.");
		}
	}

}
