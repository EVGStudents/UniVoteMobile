package ch.bfh.univotemobile.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Component for password-based AES encryption and decryption 
 * @author Raphael Haenni
 */
public class AES {
	private static final String ALGORITHM = "AES";
	
	/**
	 * Encrypts a message based on a password.
	 * @param message The message to encrypt.
	 * @param password The password to encrypt the message.
	 * @return The encrypted message.
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] message, byte[] password) throws Exception {
		Cipher c = Cipher.getInstance(ALGORITHM);
		SecretKey k = new SecretKeySpec(AES.makeKey(password), ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, k);
        return c.doFinal(message);
	}
	
	/**
	 * Decrypts an encrypted message.
	 * @param message The encrypted message.
	 * @param password The password needed to decrypt the message.
	 * @return The decrypted message.
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] message, byte[] password) throws Exception {
		Cipher c = Cipher.getInstance(ALGORITHM);
		SecretKeySpec k = new SecretKeySpec(AES.makeKey(password), ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, k);
		byte[] decryptedValue = c.doFinal(message);
		return decryptedValue;
	}
	
	private static byte[] makeKey(byte[] bkey) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bkey);
		return md.digest();
	}
}
