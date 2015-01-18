package ch.bfh.univotemobile.activities;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import ch.bfh.univotemobile.R;
import ch.bfh.univotemobile.classes.UnicertWebViewClient;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.webkit.WebView;

/**
 * Activity which handles authentication process over Switch-AAI.
 * @author Raphael Hänni
 */
public class WebViewActivity extends Activity {

	private static final String AUTHENTICATION_URL = "https://urd.bfh.ch/unicert-authentication/authenticate?idp=switchaai&params=uv-vsbfh&returnlocation=unicert://"; //oder google
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		// network exception handling
		this.trustEveryone();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		WebView webViewAuthentication = (WebView) findViewById(R.id.web_view_authentication);
		webViewAuthentication.getSettings().setJavaScriptEnabled(true);
		
		// registration activity starts is defined in MyWebViewClient
		webViewAuthentication.setWebViewClient(new UnicertWebViewClient(this));
		webViewAuthentication.loadUrl(AUTHENTICATION_URL);
	}
	
	// needed because of not trusted ssl certificate
	private void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[]{new X509TrustManager(){
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {}
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {}
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
    			}
			}}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
    	}
    }
}
