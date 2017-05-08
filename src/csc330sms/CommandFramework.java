package csc330sms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import csc330sms.broker.*;

/*
 * CommandFramework is an extend-able class that provides a framework for creating
 * commands. ApplicationLoader will load child classes of CommandFramework.
 */
class CommandFramework {
		private String name;
		private String description;
		
		private ArrayList<CommandArgument> optionalArguments;
		private ArrayList<CommandArgument> positionalArguments;
		private HashMap<String, Object> subCommands;
		
		protected StockBrokerAccount sba;
		
		/**
		 * Defines the argument list. It is important to add the CommandArgument
		 * objects in the subclass's constructor.
		 * @param sba
		 */
		public CommandFramework(StockBrokerAccount sba) {
			this.sba = sba;
			this.subCommands = null;
			this.optionalArguments = new ArrayList<CommandArgument>();
			this.positionalArguments = new ArrayList<CommandArgument>();
		}
		
		public void addPositionalArgument(String name, String description) {
			positionalArguments.add(new CommandArgument(name, description));
		}
		
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
		public boolean run(String arguments) throws InvalidArgument {
			// Break down arguments into optionalArguments and positionalArguments
			// using substrings.
			String[] posArgs = null;
			
			// If positional arguments are required, return an exception
			if (!positionalArguments.isEmpty() && arguments == null)
				throw new InvalidArgument("No argument provided.");
			
			if (arguments != null)
				posArgs = arguments.split(" ");
			
			if (subCommands != null) {
				// TODO
			}
			
			if (posArgs.length < positionalArguments.size()) {
				System.out.println("You did not provide sufficient arguments.");
				return false;
			}
			
			if (posArgs.length > positionalArguments.size()) {
				System.out.println("You provided additional positional arguments that are not valid.");
				return false;
			}
			
			for (int i = 0; i < posArgs.length; i++) {
				// Add argument value to the CommandArgument
				positionalArguments.get(i).validateArgument(posArgs[i]);
			}
			
			// TODO raise InvalidArgument exception if arguments are invalid
			
			return true;
		}
		
		/**
		 * This class is a descriptive representation (meta-data) of a command argument. Its responsibility 
		 * is to describe the argument and its function, as well as provide input validation.
		 * 
		 * This class is not meant to be extended. It should be used in the CommandFramework 
		 * constructor to define all the acceptable parameters.
		 * @author m
		 *
		 */
		class CommandArgument {
			private String name;
			private String description;
			private String argument;
			
			public CommandArgument(String name, String description) {
				this.name = name;
				this.description = description;
			}
			
			public String getName() {
				return name;
			}
			
			public String getDescription() {
				return description;
			}
			
			public boolean validateArgument(String argument) {
				// TODO Argument validation
				this.argument = argument;
				return true;
			}
			
			public String getArgument() {
				return argument;
			}
		}
		
		/** 
		 * 
		 * @return	the list of command arguments that are positional (required)
		 */
		ArrayList<CommandArgument> getPositionalArguments() {
			return positionalArguments;
		}
		
		
		/**
		 * 
		 * @return	the list of optional command arguments
		 */
		ArrayList<CommandArgument> getOptionalArguments() {
			return positionalArguments;
		}
		
		class InvalidArgument extends Exception {
			InvalidArgument(String message) {
				super(message);
			}
		}
	}