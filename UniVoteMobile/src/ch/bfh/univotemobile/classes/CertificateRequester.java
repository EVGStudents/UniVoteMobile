package ch.bfh.univotemobile.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import ch.bfh.univotemobile.interfaces.CryptographicUnicertIssuerSetup;

/**
 * This component requests the user certificate from the unicert issuer server.
 * @author Raphael Hänni
 */
public class CertificateRequester {

	private CryptographicUnicertIssuerSetup cryptographicSetup;
	private UserData userData;
	private String jSessionId;
	private URL url;
	
	/**
	 * Creates a Certificate Requester with a Cryptographic Setup.
	 * @param cryptographicSetup The Cryptographic Setup.
	 * @param userData The user data which holds important information about the registrant.
	 * @param jSessionId The jSessionId which needs to be set as a cookie. 
	 * @param url The url to where the request gets done.
	 */
	public CertificateRequester(CryptographicUnicertIssuerSetup cryptographicSetup, UserData userData, String jSessionId, URL url) {
		this.cryptographicSetup = cryptographicSetup;
		this.userData = userData;
		this.jSessionId = jSessionId;
		this.url = url;
	}
	
	/**
	 * Executes the request.
	 * @return The response from the server as String.
	 * @throws Exception
	 */
	public String doRequest() throws Exception {
		String body = this.cryptographicSetup.getBody(userData);
		HttpsURLConnection connection = (HttpsURLConnection) this.url.openConnection();
		connection.setRequestMethod( "POST" );
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Length", String.valueOf(body.length()) );
		connection.setRequestProperty("Cookie", "JSESSIONID" + "=" + this.jSessionId);
		connection.connect();
		
		OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
		writer.write(body);
		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream())); // error response

		String response = null;
		for ( String line; (line = reader.readLine()) != null; ) {	response += line;	}
		
		writer.close();
		reader.close();

		return response;
	}
}
