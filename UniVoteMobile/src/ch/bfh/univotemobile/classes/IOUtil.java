package ch.bfh.univotemobile.classes;

import java.io.*;
import android.content.Context;
import android.util.Base64;

/**
 * Component for reading/writing from / to a file on an android device.
 * @author Raphael Hänni
 */
public class IOUtil {

	/**
	 * Writes byte array to file.
	 * @param content The content which gets saved to the file.
	 * @param filename The name of the file. Can not contain path separators.
	 * @param context The file is associated with this context.
	 * @return The saved content as Base64 String.
	 * @throws IOException
	 */
	public static String writeFile(byte[] content, String filename, Context context) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
		String contentBase64 = Base64.encodeToString(content, Base64.DEFAULT);
		osw.write(contentBase64);
		osw.flush();
		osw.close();
		return contentBase64;
	}
    
	/**
	 * Reads from a file.
	 * @param filename The name of the file. Can not contain path seperators.
	 * @param context The file is associated with this context.
	 * @return The content of the file as String.
	 * @throws IOException
	 */
	public static String readFile(String filename, Context context) throws IOException {
		FileInputStream fin = context.openFileInput(filename);
		int c;
		String temp="";
		while( (c = fin.read()) != -1){
			temp = temp + Character.toString((char)c);
		}
		fin.close();
		return temp;
	}
}
