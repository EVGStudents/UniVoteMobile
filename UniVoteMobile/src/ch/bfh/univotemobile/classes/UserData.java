package ch.bfh.univotemobile.classes;

/**
 * The data of a UniVote user.
 * @author Raphael Hänni
 */
public class UserData {
	private String identityProvider;
	private String mail;
	private String uniqueIdentifier;
	private int identityFunction;
	private String applicationIdentifier;
	private String role;
	
	/**
	 * Creates new UserData.
	 * @param identityProvider The identity provider.
	 * @param mail The mail address.
	 * @param uniqueIdentifier The unique identifier of the user.
	 * @param identityFunction The identity function.
	 * @param applicationIdentifier The application identifier.
	 * @param role The role.
	 */
	public UserData(String identityProvider, String mail, String uniqueIdentifier, int identityFunction, String applicationIdentifier, String role) {
		this.identityProvider = identityProvider;
		this.mail = mail;
		this.uniqueIdentifier = uniqueIdentifier;
		this.identityFunction = identityFunction;
		this.applicationIdentifier = applicationIdentifier;
		this.role = role;
	}

	/**
	 * Returns the identity provider.
	 * @return The identity provider.
	 */
	public String getIdentityProvider() {
		return identityProvider;
	}

	/**
	 * Sets the identity provider.
	 * @param identityProvider The identity provider.
	 */
	public void setIdentityProvider(String identityProvider) {
		this.identityProvider = identityProvider;
	}

	/**
	 * Returns the mail address.
	 * @return The mail address.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail address.
	 * @param mail The mail address.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Returns the unique identifier.
	 * @return The unique identifier.
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * Sets the unique identifier.
	 * @param uniqueIdentifier The unique identifier.
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	/**
	 * Returns the identity function.
	 * @return The identity function.
	 */
	public int getIdentityFunction() {
		return identityFunction;
	}

	/**
	 * Sets the identity function.
	 * @param identityFunction The identity function.
	 */
	public void setIdentityFunction(int identityFunction) {
		this.identityFunction = identityFunction;
	}

	/**
	 * Returns the application identifier.
	 * @return The application identifier.
	 */
	public String getApplicationIdentifier() {
		return applicationIdentifier;
	}

	/**
	 * Sets the application identifier.
	 * @param applicationIdentifier The application identifier.
	 */
	public void setApplicationIdentifier(String applicationIdentifier) {
		this.applicationIdentifier = applicationIdentifier;
	}

	/**
	 * Returns the role.
	 * @return The role.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 * @param role The role.
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
