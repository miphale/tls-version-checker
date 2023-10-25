import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TLSChecker {
	 public static void main(String[] args) {
	        SSLContext context = null;
	        try {
	            KeyStore keyStore = KeyStore.getInstance("pkcs12");
	            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
	            trustManagerFactory.init(keyStore);
	            TrustManager[] trustAllCerts = trustManagerFactory.getTrustManagers();
	            context = SSLContext.getInstance("TLSv1.3");
	            context.init(null, trustAllCerts, new SecureRandom());

	            SSLParameters params = context.getSupportedSSLParameters();
	            String[] protocols = params.getProtocols();
	            System.out.println("Java version : " + System.getProperty("java.runtime.version"));
	            boolean supportsTLSv13 = false;
	            for (String protocol : protocols) {
	                if ("TLSv1.3".equals(protocol)) {
	                    supportsTLSv13 = true;
	                    break;
	                }
	            }
	            if(supportsTLSv13) {
	                System.out.println("JRE supports TLS v1.3!");
	            } else {
	                System.out.println("JRE does NOT support TLS v1.3!");
	            }
	            String[] suites = params.getCipherSuites();
	            System.out.println("A total of " + suites.length + " TLS cipher suites is supported.");
	            System.out.println(Arrays.toString(protocols));
	        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
	            e.printStackTrace();
	            System.exit(42);
	        }

	    }
}
