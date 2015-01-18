package ch.bfh.univotemobile.interfaces;

import ch.bfh.univotemobile.classes.UserData;

/**
 * Extended Cryptographic Setup needed for unicert certificate creation.
 * @author Raphael Hänni
 */
public interface CryptographicUnicertIssuerSetup extends CryptographicSetup {

	public String getBody(UserData userData);
}
