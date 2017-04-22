package csc330sms;
import java.util.*;
import java.io.Console;

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
			
		} while (run);
		
	}

	
	/*
	 * CommandFramework is an extend-able class that provides a framework for creating
	 * commands. ApplicationLoader will load child classes of CommandFramework.
	 */
	class CommandFramework {
		private String name;
		private String description;
		
		private List<CommandArgument> optionalArguments;
		private List<CommandArgument> positionalArguments;
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		/**
		 * This is the body of the command. It checks for valid arguments, and executes 
		 * the operation. The output is returned stdout.
		 * 
		 * [Invariants]
		 * @param	arguments The user input arguments
		 * @return	N/A
		 */
		public boolean run(String arguments) {
			// Break down arguments into optionalArguments and positionalArguments
			// using substrings.
			// ...
			// raise InvalidArgument exception if arguments are invalid
			
			return true;
		}
		
		/**
		 * This class is a descriptive representation of a command argument. Its responsibility 
		 * is to describe the argument and its function, as well as provide input validation.
		 * @author m
		 *
		 */
		class CommandArgument {
			private String name;
			private String description;
			
			public String getName() {
				return name;
			}
			
			public String getDescription() {
				return description;
			}
			
			public boolean validateArgument() {
				return true;
			}	
		}
		
		/** 
		 * 
		 * @return	the list of command arguments that are positional (required)
		 */
		List<CommandArgument> getPositionalArguments() {
			return positionalArguments;
		}
		
		
		/**
		 * 
		 * @return	the list of optional command arguments
		 */
		List<CommandArgument> getOptionalArguments() {
			return positionalArguments;
		}
	}
	
	ApplicationLoader() {
		commandTable = new HashMap<String, ArrayList<Object>>();
		
		// EXAMPLE: Suppose we have a command that extends CommandFramework
		CommandFramework buy = new CommandFramework();
		
		ArrayList<Object> buyCmd = new ArrayList<Object>();
		buyCmd.add("buy");
		buyCmd.add(buy);
		commandTable.put("buy", buyCmd);
		// END EXAMPLE
	}

	public static void main(String[] args) {	
		ApplicationLoader apploader = new ApplicationLoader();
		apploader.runLoop();
	}


}