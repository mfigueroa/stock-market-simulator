package csc330sms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.util.HashMap;
import java.util.List;
import csc330sms.broker.*;

/*
 * CommandFramework is an extend-able class that provides a framework for creating
 * commands. ApplicationLoader will load child classes of CommandFramework.
 */
public class CommandFramework {
		private String name;
		private String description;
		
		/**
		 * Optional arguments object is a map since we can't infer which parameter each element belongs to.
		 */
		private HashMap<String, CommandArgument> optionalArguments;
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
			this.optionalArguments = new HashMap<String, CommandArgument>();
			this.positionalArguments = new ArrayList<CommandArgument>();
		}
		
		/**
		 * Adds an optional PARAMETER that can be optionally supplied to the command.
		 * @param name
		 * @param description
		 * @param type
		 */
		public void addOptionalArgument(String name, String description, ValidationType type) {
			optionalArguments.put(name, new CommandArgument(name, description, type));
		}
		
		/**
		 * Add a required (positional) PARAMETER that must be supplied to the command.
		 * @param name
		 * @param description
		 * @param type
		 */
		public void addPositionalArgument(String name, String description, ValidationType type) {
			positionalArguments.add(new CommandArgument(name, description, type));
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
			ArrayList<String> posArgs = new ArrayList<String>();
			
			// If positional arguments are required, return an exception
			if (!positionalArguments.isEmpty() && arguments == null)
				throw new InvalidArgument("No argument provided.");
			
			// Verify that we have arguments
			if (arguments != null)
				posArgs = new ArrayList<String>(Arrays.asList(arguments.split(" ")));
			
			if (subCommands != null) {
				// TODO Subcommands
			}
			
			// Catch positional and optional arguments
			// Capture groups: parameter, argument
			Pattern p = Pattern.compile("--([a-zA-Z0-9])+=([a-zA-Z0-9])+");
			
			// Temporary list to store optional arguments that will be removed from posArgs
			ArrayList<String> optionalArgs = new ArrayList<String>();
			
			for (int i = 0; i < posArgs.size(); i++) {
				Matcher m = p.matcher(posArgs.get(i));
				if (m.find()) {
					optionalArguments.get(m.group(0)).validateArgument(m.group(1));
					optionalArgs.add(posArgs.get(i));
				}
			}
			
			// Remove optional arguments from posArgs
			for (int i = 0; i < optionalArgs.size(); i++) {
				for (int j = 0; i < posArgs.size(); j++) {
					if (posArgs.get(i) == optionalArgs.get(i)) {
						// Remove optional argument
						posArgs.remove(i);
					}
				}
			}
			
			if (posArgs.size() < positionalArguments.size()) {
				System.out.println("You did not provide sufficient arguments.");
				return false;
			}
			
			if (posArgs.size() > positionalArguments.size()) {
				System.out.println("You provided additional positional arguments that are not valid.");
				return false;
			}
			
			// Order of arguments is implied
			for (int i = 0; i < posArgs.size(); i++) {
				// Add argument value to the CommandArgument
				positionalArguments.get(i).validateArgument(posArgs.get(i));
			}
			
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
			private Object argument;
			private ValidationType type;
			
			public CommandArgument(String name, String description, ValidationType type) {
				this.name = name;
				this.description = description;
				this.type = type;
			}
			
			public String getName() {
				return name;
			}
			
			public String getDescription() {
				return description;
			}
			
			/**
			 * Validates argument. If argument is valid, the argument is saved.
			 * @param argument
			 * @param type
			 * @return
			 * @throws InvalidArgument
			 */
			public boolean validateArgument(String argument) throws InvalidArgument {
				if (type == ValidationType.STRING)
					this.argument = (String)argument;
				else if (type == ValidationType.INT) {
					try {
						this.argument = Integer.parseInt(argument);
					} catch (java.lang.NumberFormatException e) {
						throw new InvalidArgument("Expected integer argument.");
					}
				}
				return true;
			}
			
			public Object getArgument() {
				return argument;
			}
		}
		
		/**
		 * Variable type for argument validation.
		 * @author m
		 *
		 */
		public enum ValidationType {
			STRING, INT, BIGDECIMAL
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
		HashMap<String, CommandArgument> getOptionalArguments() {
			return optionalArguments;
		}
		
		public class InvalidArgument extends Exception {
			InvalidArgument(String message) {
				super(message);
			}
		}
	}