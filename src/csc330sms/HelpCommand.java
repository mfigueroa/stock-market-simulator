package csc330sms;

import csc330sms.CommandFramework.InvalidArgument;
import csc330sms.broker.StockBrokerAccount;
import java.util.*;

/**
 * Meta command that prints information of other commands.
 * @author m
 *
 */
public class HelpCommand extends CommandFramework {
	
	private HashMap<String, ArrayList<Object>> commandTable;

	public HelpCommand(HashMap<String, ArrayList<Object>> commandTable) {
		super(null);
		this.commandTable = commandTable;
	}
	
	@Override
	public boolean run(String arguments) throws InvalidArgument {
		if (!super.run(arguments)) return false;
		
		if (arguments == null) {
			for(HashMap.Entry row : commandTable.entrySet()) {
				System.out.printf("%s\n", row.getKey());
				System.out.printf("%10s	%s\n", "ARGUMENT", "DESC");
				
				// commandTable is HashMap<String, ArrayList<Object>>
				ArrayList<Object> meta = (ArrayList<Object>)row.getValue();
				CommandFramework cmd = (CommandFramework)meta.get(1);
				
				ArrayList<CommandArgument> parameters = cmd.getPositionalArguments();
				
				// Iterate through positional arguments
				for (int j = 0; j < parameters.size(); j++) {
					System.out.printf("%10s	%s\n", parameters.get(j).getName().toString(),
							parameters.get(j).getDescription().toString());
				}
				System.out.println("\n");
			}
		}
		
		return true;
	}
}
