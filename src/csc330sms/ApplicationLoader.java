package csc330sms;
import java.util.*;
import csc330sms.broker.*;
import java.io.Console;
import java.math.*;

/**
 * 
 * Main entry point of the application.
 * 
 *
 */
public class ApplicationLoader {
	
	/*
	 * commandTable is a map of all the commands that are available to the user.
	 * The first object in ArrayList should be the name of the command as a String.
	 * The second object in ArrayList should be an instance of the command class.
	 */
	HashMap<String, ArrayList<Object>> commandTable;
	
	public void runLoop() {
		System.out.println("CSC 330 Stock Market Simulator\n");

		Scanner keyboard = new Scanner(System.in);
		boolean run = true;
		
		// Main command loop
		do {
			System.out.print("> ");
			String input = keyboard.nextLine();
			String[] cmdInput = input.split(" ");
			
			// Attempt to find command inputed by user
			ArrayList<Object> command = commandTable.get(cmdInput[0]);
			
			if (input.isEmpty()) {
				continue;
			}
			else if (input.equals("version")) {
				System.out.println("You are running version 0.1a.");
			}
			else if (input.equals("exit")) {
				run = false;
				System.out.println("Exiting.");
			}
			else if (command == null) {
				System.out.println("Command not found.");
			}
			else {
				CommandFramework cmd = (CommandFramework)command.get(1);
				cmd.run(input.substring(cmdInput[0].length() + 1));
			}
			
		} while (run);
		
	}
	
	ApplicationLoader() {
		StockBrokerAccount sba = new StockBrokerAccount(new BigDecimal(10000));
		
		commandTable = new HashMap<String, ArrayList<Object>>();
		
		// EXAMPLE: Suppose we have a command that extends CommandFramework
		CommandFramework buy = new BuyCommand(sba);
		ArrayList<Object> buyCmdMeta = new ArrayList<Object>();
		buyCmdMeta.add("buy");
		buyCmdMeta.add(buy);
		commandTable.put("buy", buyCmdMeta);
		// END EXAMPLE
	}

	public static void main(String[] args) {	
		ApplicationLoader apploader = new ApplicationLoader();
		apploader.runLoop();
	}


}