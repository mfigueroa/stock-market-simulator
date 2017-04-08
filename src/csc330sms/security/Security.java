package csc330sms.security;

/**
 * 
 * Security is the generalization of stocks, options, bonds, and any other stock market instrument.
 * It is a blueprint for how stocks (and other instruments) should be implemented.
 * 
 * Java interfaces do not have attributes (variables).
 */
public interface Security {
	String getCompany();
	int getQuantity();
	String getSymbol();
}
