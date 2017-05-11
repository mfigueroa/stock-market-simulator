package csc330sms;
import java.util.*;

import org.jfree.ui.RefineryUtilities;

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
	
	/**
	 * Adds a command to the command table.
	 * @param token
	 * @param command
	 */
	public void addCommand(String token, Object command) {
		ArrayList<Object> meta = new ArrayList<Object>();
		meta.add(token);
		meta.add(command);
		commandTable.put(token, meta);
	}
	
	/**
	 * Finds and returns a command from the command table.
	 * @param input
	 * @return An array with two values: String token, Object command
	 * @throws CommandNotFound
	 */
	public ArrayList<Object> getCommandTuple(String token) throws CommandNotFound {
		// Attempt to find command inputed by user
		ArrayList<Object> command = commandTable.get(token);
		
		if (command == null)
			throw new CommandNotFound();
		
		return command;
	}
	
	public void runLoop() {
		System.out.println(" ____  __  __ ____  ");
		System.out.println("/ ___||  \\/  / ___| ");
		System.out.println("\\___ \\| |\\/| \\___ \\ ");
		System.out.println(" ___) | |  | |___) |");
		System.out.println("|____/|_|  |_|____/ ");
		System.out.println("Stock Market Simulator\n");
		System.out.println("Type \"help\" for a list of commands.\n");

		Scanner keyboard = new Scanner(System.in);
		boolean run = true;
		
		// Main command loop
		do {
			System.out.print("> ");
			String input = keyboard.nextLine();
			String[] cmdInput = input.split(" ");
			
			// Attempt to find command inputed by user
			//ArrayList<Object> command = commandTable.get(cmdInput[0]);
			
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
			else {	
				try {
					ArrayList<Object> command = getCommandTuple(cmdInput[0]);
					
					CommandFramework cmd = (CommandFramework)command.get(1);
					
					if (cmdInput.length > 1)
						cmd.run(input.substring(cmdInput[0].length() + 1));
					else
						cmd.run(null);
				} catch (CommandFramework.InvalidArgument e) {
					System.out.println(e.getMessage());
				} catch (CommandNotFound e) {
					System.out.println("Command not found.");
				}
			}
			
		} while (run);
		
	}
	
	public class CommandNotFound extends Exception {
		CommandNotFound() {
			super("The command was not found.");
		}
	}
	
	public ApplicationLoader() {
		StockBrokerAccount sba = new StockBrokerAccount(new BigDecimal(10000));
		commandTable = new HashMap<String, ArrayList<Object>>();
		
		addCommand("buy", new BuyCommand(sba));
		addCommand("account", new AccountCommand(sba));
		addCommand("quote", new QuoteCommand(sba));
		addCommand("sell", new SellCommand(sba));
		addCommand("find", new FindCommand(sba));
		addCommand("help", new HelpCommand(commandTable));
	}

	public static void main(String[] args) {	
		ApplicationLoader apploader = new ApplicationLoader();
		apploader.runLoop();
	}


}